class Brush

  attr_reader :parts

  def initialize
    @parts = []
  end

  def select(part)
    @parts << part if part
  end

  def deselect(part)
    @parts = @parts.delete_if {|p| p == part }
  end

  def deselect_all
    @parts = []
  end

  def move(x, y, z)
    @parts.each do |p|
      p.move x, y, z
    end
  end

  def grow(x, y, z)
    @parts.each do |p|
      p.change_size(x, y, z)
    end
  end

end
