require 'construct'
require 'brush'

class Workspace

  attr_accessor :construct
  attr_accessor :brush

  def initialize
    @brush = Brush.new
  end

  def new_construct
    @construct = Construct.new
  end

  def construct_name=(name)
    @construct.name = name
  end

  def select_part_at(x, y, z)
    @brush.select @construct.find_part_at(x, y, z)
  end

  def deselect_part_at(x, y, z)
    @brush.deselect @construct.find_part_at(x, y, z)
  end

  def delete_selected_parts
    @brush.parts.each do |p|
      @construct.delete_part p
    end
    @brush.deselect_all
  end

end
