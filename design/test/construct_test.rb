require 'minitest/spec'
require 'minitest/autorun'

require 'construct'
require 'part'

describe Construct do
  it "has a name" do
    Construct.new.name.must_equal ""
  end

  # TEST: Construct#name=("Construct!")
  it "can have it's name changed" do
    c = Construct.new
    c.name = "Construct!"
    c.name.must_equal "Construct!"
  end

  describe "parts" do

    it "has many parts" do
      Construct.new.parts.must_equal []
    end

    it "can take parts in constructor" do
      p1 = Part.new
      p2 = Part.new
      c = Construct.new [p1, p2]

      c.parts.must_equal [p1, p2]
    end

    it "can be given another part" do
      c = Construct.new
      p = Part.new
      c.add_part p
      c.parts.must_equal [p]
    end

    # TEST: Construct#delete_part(Part)
    it "can be told to drop a part" do
      p1 = Part.new
      p2 = Part.new
      c = Construct.new [p1, p2]

      c.delete_part p1

      c.parts.must_equal [p2]
    end

    it "ignores requests to delete the nil part" do
      p1 = Part.new
      c = Construct.new [p1]

      c.delete_part nil

      c.parts.must_equal [p1]
    end

    # TEST: Construct#find_part_at(0, 0, 0) -> Part
    it "can find a part at a given location" do
      p1 = Part.new 0, 0, 0
      p2 = Part.new 1, 1, 1
      c = Construct.new [p1, p2]

      c.find_part_at(0, 0, 0).must_equal p1
    end

    # TEST: Construct#find_part_at(0, 0, 0) -> nil
    it "returns nil if no part is found at requested location" do
      c = Construct.new
      c.find_part_at(0, 0, 0).must_be_nil
    end

  end

end
