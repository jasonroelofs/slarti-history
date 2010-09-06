require "fileutils"
include FileUtils

task :default => [:build, :install]

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
