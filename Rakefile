task :default => :run

MAIN_CLASS_FILE = "build/SimpleApp.class"

file MAIN_CLASS_FILE => ["src/simple_app.mirah"] do
  system("ant")
end

def classpath(base = "")
  (FileList["#{base}lib/**/*.jar"] - FileList["#{base}lib/mirah-complete.jar"]).join(":")
end

desc "Run our app"
task :run => [MAIN_CLASS_FILE] do
  cd "build" do
    cmd = "java -cp #{classpath("../")}:. SimpleApp"
    puts cmd
    system(cmd)
  end
end

