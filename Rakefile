#!/usr/bin/env ruby

$: << File.expand_path(File.join("packages"), File.dirname(__FILE__))
$: << File.expand_path(File.join("packages", "lib"), File.dirname(__FILE__))

require 'packages/lib/packages'

# Setup some global Ogre.rb information
PROJECT_ROOT = File.expand_path(File.dirname(__FILE__))

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

