require 'construct'

class Workspace

  attr_accessor :construct
  attr_accessor :selected_part

  def new_construct
    @construct = Construct.new
  end

  def construct_name=(name)
    @construct.name = name
  end

  def select_part_at(x, y, z)
    @selected_part = @construct.find_part_at x, y, z
  end

  def move_selected_part(x, y, z)
    @selected_part.move x, y, z if @selected_part
  end

  def grow_selected_part(x, y, z)
    @selected_part.change_size x, y, z if @selected_part
  end

  def deselect_part
    @selected_part = nil
  end

  def delete_part
    @construct.drop_part @selected_part
    deselect_part
  end

end
