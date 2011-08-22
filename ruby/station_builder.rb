=begin
  Basic structure of the starting station, from the side:

  -------------                   -------------
  |           |------------------ |           |
  |                                           |
  |                                           |
  ------------------------------- -------------

  The left structure will be smaller and will be the living
  quarters and control center.

  The right structure will be larger, be the hanger and storage.

  Hallway connects the two, will be one, maybe 2, units wide

  -----

  Note on positioning (looking from the screen, +x -> and +y ^, +z into screen):
    width => x
    height => y
    depth => z

  -----

  The grid is defined in 0.25 unit cubes. All panels snap to that

  -----

  This file outputs SQL statements to run against sqlite db

=end

# Room definitions define the empty space, walls will be put
# on the next grid location outside of area

class Vector < Struct.new(:x, :y, :z)
  def to_query
    "v3:#{self.x},#{self.y},#{self.z}"
  end
end

$station_id = ARGV.pop

control_room = {
  :width => 6,
  :depth => 6,
  :height => 4,
  :center => Vector.new(-8, 0, 0)
}

hallway = {
  :width => 10,
  :depth => 3,
  :height => 4,
  :center => Vector.new(0, 0, 0)
}

hangar = {
  :width => 20,
  :depth => 30,
  :height => 6,
  :center => Vector.new(15, 0, 0)
}

def build_panel(from_point, to_point)
  query = %|insert into panels (station_id, from_point, to_point, material) values (#{$station_id}, "#{from_point.to_query}", "#{to_point.to_query}");|
  puts query
end

[hallway].each do |section|
  center = section[:center]
  height = section[:height]
  width = section[:width]
  depth = section[:depth]

  h_h = height / 2
  h_w = width / 2
  h_d = depth / 2

  # Floor (-y)
  build_panel Vector.new(center.x - width, center.y - h_h, center.z - depth),
    Vector.new(center.x + width, center.y - h_h, center.z + depth)

  # Ceiling (+y)
  build_panel Vector.new(center.x - width, center.y + h_h, center.z - depth),
    Vector.new(center.x + width, center.y + h_h, center.z + depth)

  # -x Wall
  # +x Wall
  # +z Wall
  # -z Wall

end
