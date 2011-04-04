package :ogre_procedural do |rocket|
  rocket.download = "http://ogre-procedural.googlecode.com/files/ogre-procedural_0.1_source.7z"
  rocket.download_to = "ogre-procedural_0.1_source.7z"

  rocket.unpack = "7za x -y -oogre_procedural"
  rocket.unpack_to = "ogre_procedural"

  rocket.build do |opts|
    opts.all do
      # Can't get this thing to build on it's own, but I don't need that.
      # Copy the source and header files into my source tree.

      proj_root = "../../"
      src_dir = File.join(proj_root, "src", "procedural")
      inc_dir = File.join(proj_root, "include", "procedural")

      FileUtils.mkdir src_dir unless File.directory?(src_dir)
      FileUtils.mkdir inc_dir unless File.directory?(inc_dir)

      sh "cp library/src/* #{src_dir}/"
      sh "cp library/include/* #{inc_dir}/"
    end
  end
end
