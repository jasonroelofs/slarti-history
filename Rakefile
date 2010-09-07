require "fileutils"
include FileUtils

task :default => [:build, :install]

desc "Rebuild everything"
task :rebuild_all => ["deps:rebuild_all", :clean, :build, :install]

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
  cd "build" do
    sh "xcodebuild"
  end
end

desc "Clean up the build"
task :clean do
  rm "bin/slartibartfast"
  cd "build" do
    sh "xcodebuild clean"
  end
end

desc "Install built binary"
task :install do
  cp "build/Debug/slartibartfast", "bin/"
end

desc "Run the app"
task :run do
  cd "bin" do
    sh "./slartibartfast"
  end
end

namespace :deps do

  desc "Build all dependencies"
  task :build => [:qtogre]

  desc "Rebuild all vendored dependencies"
  task :rebuild_all => [:clean_qtogre, :qtogre]

  desc "Build QtOgre"
  task :qtogre do
    cd "vendor/QtOgre" do
      mkdir_p "build"
      cd "build" do
        sh "cmake -G Xcode ../"
        sh "xcodebuild"
      end
    end
  end

  desc "Clean out QtOgre"
  task :clean_qtogre do
    rm_rf "vendor/QtOgre/build"
  end

  desc "List out the required dependencies"
  task :list do
    puts "Ogre 1.7.x (I use the pre-built SDK)"
    puts "OGRE_HOME set (see ./environment)"
    puts "Qt 4.6.x (Cocoa dmg build for Mac)"
  end


end
