require 'geometry'

class Part

  attr_reader :location, :geometry, :size

  def initialize(x = 0, y = 0, z = 0)
    @location = [x, y, z]
    @geometry = Geometry.new
    @size = [1, 1, 1]
  end

  def move(x, y, z)
    @location[0] += x
    @location[1] += y
    @location[2] += z
  end

  def change_size(x, y, z)
    @size[0] += x
    @size[1] += y
    @size[2] += z
  end

end
