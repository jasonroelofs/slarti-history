require 'minitest/spec'
require 'minitest/autorun'

require 'part'

describe Part do

  it 'exists' do
    Part.new.wont_be_nil
  end

  it "can take a default location" do
    p = Part.new 1, 2, 3
    p.location.must_equal [1, 2, 3]
  end

  describe "geometry" do

    it "has one" do
      Part.new.geometry.wont_be_nil
      Part.new.geometry.must_be_kind_of Geometry
    end

  end

  describe "size" do

    it "has a size" do
      Part.new.size.wont_be_nil
    end

    it "defaults to unit cube" do
      Part.new.size.must_equal [1, 1, 1]
    end

    # TEST: Part#change_size(2, 3, 4)
    it "can be made bigger" do
      p = Part.new
      p.change_size 2, 3, 4

      p.size.must_equal [3, 4, 5]
    end

    it "respects other size change requests" do
      p = Part.new
      # Ick fix name. Needs to be similar to
      # #move but in terms of size. Scale is usually
      # considered a multiplicative action, and what we
      # want here is additive
      p.change_size 1, 0, 0
      p.change_size 0, 1, 0

      p.size.must_equal [2, 2, 1]
    end

  end

  describe "location" do

    it "has a location" do
      Part.new.location.wont_be_nil
    end

    it "defaults to the origin" do
      Part.new.location.must_equal [0, 0, 0]
    end

    # TEST: Part#move(1, 2, 3)
    it "can be moved" do
      p = Part.new
      p.move 1, 2, 3

      p.location.must_equal [1, 2, 3]
    end

    it "keeps track of previous movements" do
      p = Part.new
      p.move 1, 0, 0
      p.move 0, 1, 0
      p.move 0, 0, 1

      p.location.must_equal [1, 1, 1]
    end

  end

end
