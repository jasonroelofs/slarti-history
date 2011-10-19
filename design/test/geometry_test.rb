require 'minitest/spec'
require 'minitest/autorun'

require 'geometry'

describe Geometry do

  it "exists" do
    Geometry.new.wont_be_nil
  end

  it "has a mesh" do
    Geometry.new.mesh.wont_be_nil
  end

  it "has a material" do
    Geometry.new.material.wont_be_nil
  end

  it "can be given a different material" do
    g = Geometry.new
    g.material = "Some other material"

    g.material.must_equal "Some other material"
  end

end

