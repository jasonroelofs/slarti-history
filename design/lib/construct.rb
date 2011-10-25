class Construct

  attr_reader :parts
  attr_accessor :name

  def initialize(parts = [])
    @parts = parts
    @name = ""
  end

  def add_part(p)
    @parts << p
  end

  def delete_part(p)
    @parts = @parts.reject {|part| part == p }
  end

  def find_part_at(x, y, z)
    @parts.find {|p| p.location == [x, y, z] }
  end

end
