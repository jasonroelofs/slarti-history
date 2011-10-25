require 'minitest/spec'
require 'minitest/autorun'

require 'workspace'
require 'part'

describe Workspace do

  it "exists" do
    Workspace.new.wont_be_nil
  end

  it "has a brush" do
    Workspace.new.brush.wont_be_nil
  end

  it "can have one construct" do
    Workspace.new.construct.must_be_nil
  end

  it "can create a new construct" do
    w = Workspace.new
    w.new_construct

    w.construct.must_be_kind_of Construct
  end

  it "can use an existing construct" do
    c = Construct.new
    w = Workspace.new
    w.construct = c

    w.construct.must_equal c
  end

  # MOCK: Construct#name=("Construct!")
  it "can change name of construct" do
    w = Workspace.new
    mock = MiniTest::Mock.new
    mock.expect(:name=, nil, ["Construct!"])

    w.construct = mock

    w.construct_name = "Construct!"

    mock.verify
  end

  describe "parts" do

    before do
      @construct = MiniTest::Mock.new
      @brush = MiniTest::Mock.new
      @workspace = Workspace.new
      @workspace.construct = @construct
      @workspace.brush = @brush
    end

    after do
      @construct.verify
      @brush.verify
    end

    # MOCK: Construct#find_part_at(0, 0, 0) -> Part
    # MOCK: Brush#select(Part)
    it "can select a Part from the construct" do
      p = Part.new
      @construct.expect(:find_part_at, p, [0, 0, 0])
      @brush.expect(:select, nil, [p])

      @workspace.select_part_at 0, 0, 0
    end

    # MOCK: Construct#find_part_at(0, 0, 0) -> nil
    # MOCK: Brush#select(nil)
    it "handles not finding a Part" do
      p = Part.new
      @construct.expect(:find_part_at, nil, [0, 0, 0])
      @brush.expect(:select, nil, [nil])

      @workspace.select_part_at 0, 0, 0
    end

    # MOCK: Brush#deselect(Part)
    it "can deselect the selected part" do
      p = Part.new
      @construct.expect(:find_part_at, p, [0, 0, 0])
      @brush.expect(:deselect, nil, [p])

      @workspace.deselect_part_at 0, 0, 0
    end

#    it "can add a new Part to the Construct"
#
#    describe "multi-select" do
#
#      it "can find multiple Parts at once"
#
#      it "can move multiple Parts at once"
#
#      it "can resize mutilple Parts at once"
#
#    end

  end

end
