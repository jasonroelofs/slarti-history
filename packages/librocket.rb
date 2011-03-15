package :librocket do |rocket|
  rocket.download = "http://librocket.com/download/40?startdownload"
  rocket.download_to = "librocket_generic-source-1.2.1.zip"

  rocket.unpack = "tar jxvf"
  rocket.unpack_to = "librocket"

  rocket.build do |opts|
    opts.all do
      cd "Build" do
        sh "cmake -DBUILD_SHARED_LIBS=FALSE -DCMAKE_INSTALL_PREFIX=#{opts.prefix}"
        sh "make -j 3"
        sh "make install"
      end
    end
  end
end
