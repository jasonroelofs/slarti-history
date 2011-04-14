package :yaml_cpp do |yaml|
  yaml.download = "http://yaml-cpp.googlecode.com/files/yaml-cpp-0.2.6.tar.gz"
  yaml.download_to = "yaml-cpp-0.2.6.tar.gz"

  yaml.unpack = "tar jxvf"
  yaml.unpack_to = "yaml-cpp"

  yaml.build do |opts|
    opts.all do
      mkdir "Build"
      cd "Build" do
        sh "cmake -DBUILD_SHARED_LIBS=FALSE -DAPPLE_UNIVERSAL_BIN=TRUE -DCMAKE_INSTALL_PREFIX=#{opts.prefix} ../"
        sh "make -j 3"
        sh "make install"
      end
    end
  end
end
