require 'platform'

#
# Ogre.rb wrapper definition helper system
# This provides a rake-ish DSL for defining how to
# setup and build a library to be ready for wrapping
#
class Package
  attr_accessor :download_to, :download, :unpack, :unpack_to, :build_block, :patches

  attr_accessor :wrapper

  attr_accessor :build_dir

  def initialize(name)
    @name = name
    @wrapper = true
  end

  def build(&block)
    @build_block = block
  end

  def patch(*patches)
    @patches = patches
  end

  def process_patches
    patch_dir = base_path("wrappers", @name, "patch")
    patches.each do |patch|
      patch_file = "#{patch_dir}/#{patch}.patch"
      raise "Could not find file #{patch_file}" unless File.exists?(patch_file)
      puts "Patching #{@name} with #{patch_file}"
      sh "patch -s -N -i #{patch_file} -p0"
    end
  end

end

# Handles figuring out environment and platform variables
class Opts
  attr_accessor :prefix

  def initialize(opts = {})
    @prefix = opts.delete(:prefix)
    @build_block = opts.delete(:build)

    @build_block.call(self)
  end

  def build
    if Platform.mac?
      @mac_build.call
    elsif Platform.windows?
      @windows_build.call
    else
      @linux_build.call
    end
  end

  ##
  # Mac specific helpers
  ##

  SDK_BASE = "/Developer/SDKs"

  # Find out what the latest SDK is sitting in
  # /Developer/SDKs and return the full path to it
  def latest_sdk
    Dir["#{SDK_BASE}/MacOSX*.sdk"].sort.last
  end

  ##
  # *Nix specific helpers
  ##

  ##
  # Windows specific helpers
  ##

  ##
  # The following define build block steps for the
  # appropriate platform
  ##

  def mac(&block)
    @mac_build = block
  end

  def linux(&block)
    @linux_build = block
  end

  def windows(&block)
    @windows_build = block
  end
end


def base_path(*args)
  File.join(PROJECT_ROOT, *args)
end

def package(lib)
  if lib.is_a?(Hash)
    library = lib.keys[0]
    dependencies = lib[library]
  else
    library = lib.to_s
    dependencies = []
  end

  namespace library do
    package = Package.new(library)
    yield package

    Packages.add(lib, package)

    ##
    # Cleaning things up
    ##
    desc "Clean up #{library}"
    task :clean => [:clean_download, :clean_unpack] 
    
    task :clean_download do
      rm_f base_path("tmp", "downloads", "#{package.download_to}")
    end

    task :clean_unpack do
      rm_rf base_path("tmp", package.unpack_to)
    end

    # Build our list of dependencies, make sure they run first
    deps = ["base:bootstrap", "#{library}:clean_unpack", dependencies.map {|d| "#{d}:setup"}].flatten

    desc "Download, build, and install the #{library} library. Installs into #{PROJECT_ROOT}/ext"
    task :setup => deps do
      cd base_path("tmp") do

        # Download
        cd "downloads" do
          unless File.exists?(package.download_to)
            sh "wget #{package.download}"
          end
        end

        # Unpack
        sh "#{package.unpack} downloads/#{package.download_to}"

        # Fix any odd permissions
        sh "chmod -R u+w #{package.unpack_to}"

        package.build_dir = File.join(`pwd`.strip, package.unpack_to)

        if package.build_block
          cd package.unpack_to do
            # Patch / Build / install
            package.process_patches if package.patches
            Opts.new(
              :prefix => base_path("ext"),
              :build => package.build_block
            ).build
          end
        end
      end
    end
  end
end
