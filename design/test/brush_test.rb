require 'minitest/spec'
require 'minitest/autorun'

require 'brush'
require 'part'

describe Brush do

  before do
    @brush = Brush.new
  end

  it "exists" do
    @brush.wont_be_nil
  end

  # TEST: Brush#parts -> Array
  it "has a list of parts" do
    @brush.parts.must_equal []
  end

  # TEST: Brush#select(Part)
  it "can be given a part to work on" do
    p = Part.new
    @brush.select p

    @brush.parts.must_equal [p]
  end

  # TEST: Brush#select(nil)
  it "ignores request to work with nil" do
    @brush.select nil

    @brush.parts.must_equal []
  end

  # TEST: Brush#deselect(Part)
  it "can be told to stop working on a part" do
    p = Part.new
    @brush.select p
    @brush.deselect p

    @brush.parts.must_equal []
  end

  it "handles requests to remove nil part from list" do
    p = Part.new
    @brush.select p
    @brush.deselect nil

    @brush.parts.must_equal [p]
  end

  # TEST: Brush#deselect_all
  it "can be told to deselect all parts" do
    p = Part.new
    @brush.select p
    @brush.select p
    @brush.select p

    @brush.deselect_all

    @brush.parts.must_equal []
  end

  describe "manipulating parts" do

    before do
      @part1 = MiniTest::Mock.new
      @part2 = MiniTest::Mock.new

      @brush.select @part1
      @brush.select @part2
    end

    # MOCK: Part#move(1, 2, 3)
    it "can move the selected Part" do
      @part1.expect(:move, nil, [1, 2, 3])
      @part2.expect(:move, nil, [1, 2, 3])

      @brush.move 1, 2, 3
    end

    # MOCK: Part#change_size(2, 3, 4)
    it "can resize the selected Part" do
      @part1.expect(:change_size, nil, [2, 3, 4])
      @part2.expect(:change_size, nil, [2, 3, 4])

      @brush.grow 2, 3, 4
    end

  end
end
