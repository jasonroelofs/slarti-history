require 'minitest/spec'
require 'minitest/autorun'

require 'workspace'
require 'part'

describe Workspace do

  it "exists" do
    Workspace.new.wont_be_nil
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
      @workspace = Workspace.new
      @workspace.construct = @construct
    end

    after do
      @construct.verify
    end

    it "has a selected part to work on" do
      @workspace.selected_part.must_be_nil
    end

    # MOCK: Construct#find_part_at(0, 0, 0) -> Part
    it "can ask a construct for a Part" do
      p = Part.new
      @construct.expect(:find_part_at, p, [0, 0, 0])

      @workspace.select_part_at 0, 0, 0
      @workspace.selected_part.must_equal p
    end

    it "can be told directly what part to work on" do
      p = Part.new
      @workspace.selected_part = p
      @workspace.selected_part.must_equal p
    end

    # MOCK: Construct#find_part_at(0, 0, 0) -> nil
    it "handles not finding a Part" do
      p = Part.new
      @construct.expect(:find_part_at, nil, [0, 0, 0])

      @workspace.select_part_at 0, 0, 0
      @workspace.selected_part.must_be_nil
    end

    describe "manipulating a selected part" do

      before do
        @part = MiniTest::Mock.new
        @workspace.selected_part = @part
      end

      after do
        @part.verify
      end

      # MOCK: Part#move(1, 2, 3)
      it "can move the selected Part" do
        @part.expect(:move, nil, [1, 2, 3])
        @workspace.move_selected_part 1, 2, 3
      end

      # MOCK: Part#change_size(2, 3, 4)
      it "can resize the selected Part" do
        @part.expect(:change_size, nil, [2, 3, 4])
        @workspace.grow_selected_part 2, 3, 4
      end

      it "can deselect the selected part" do
        @workspace.deselect_part
        @workspace.selected_part.must_be_nil
      end

      describe "deleting the selected part" do

        # MOCK: Construct#drop_part(p)
        it "removes the part from the Construct and deselects it" do
          @construct.expect(:drop_part, nil, [@part])
          @workspace.delete_part
          @workspace.selected_part.must_be_nil
        end

      end
    end

    describe "trying to manipulate no selected part" do
      it "ignores requests to move" do
        @workspace.move_selected_part 1, 2, 3
      end

      it "ignores requests to resize" do
        @workspace.grow_selected_part 2, 3, 4
      end

      # MOCK: Construct#drop_part(nil)
      it "is fine dropping a non selected part" do
        @construct.expect(:drop_part, nil, [nil])
        @workspace.delete_part
      end
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
