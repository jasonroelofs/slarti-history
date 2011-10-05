
desc "List all TODOs"
task :todos do
  puts `grep -R "TODO" src/`
end

desc "List all HACKs"
task :hacks do
  puts `grep -R "HACK" src/`
end

desc "List all FIXMEs"
task :fixmes do
  puts `grep -R "FIXME" src/`
end

JME_SOURCE = "/Users/roelofs/Source/jmonkeyengine/engine/dist"

desc "Copy over libs from jME source dir to our local lib"
task :update_jme do
  cp File.join(JME_SOURCE, "jMonkeyEngine3.jar"), "lib/"
  `cp #{File.join(JME_SOURCE, "lib", "*")} lib/jme3/`
end
