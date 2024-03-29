#!/usr/bin/env ruby

$: << File.expand_path(File.join("packages"), File.dirname(__FILE__))
$: << File.expand_path(File.join("packages", "lib"), File.dirname(__FILE__))

require 'packages/lib/packages'

# Setup some global Ogre.rb information
PROJECT_ROOT = File.expand_path(File.dirname(__FILE__))
PATH_TO_BIN = "build/Debug/slartibartfast.app/Contents/MacOS"

# Need to run through the 'packages' dir, running the package definitions
# and building rake tasks as needed
Packages.load_packages

# Global ogre.rb tasks
namespace :base do

  # Make sure the build and install directory structure we expect exists
  task :bootstrap do
    mkdir_p base_path("tmp", "downloads")
    mkdir_p base_path("ext")
    mkdir_p base_path("ext/frameworks")
  end

end

task :default => [:build, :run]

desc "Re-run cmake"
task :configure do
  mkdir_p "build"
  cd "build" do
    sh "cmake -G Xcode ../"
  end
end

desc "Build slartibartfast"
task :build do
  mkdir_p "build"
  if File.directory?(PATH_TO_BIN)
    cd PATH_TO_BIN do
      rm_f "slartibartfast"
    end
  end

  cd "build" do
    system("xcodebuild | grep -v setenv")
  end
end

desc "Clean up the build"
task :clean do
  cd "build" do
    sh "xcodebuild clean"
  end
end

desc "Run the app"
task :run do
  cd PATH_TO_BIN do
    sh "./slartibartfast"
  end
end
