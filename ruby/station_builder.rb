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

  def *(val)
    Vector.new(
      self.x * val,
      self.y * val,
      self.z * val
    )
  end
end

$station_id = ARGV.pop

##
# These define the INNER available area of the
# constructs. The script will put walls around
# this area, 2 grid blocks thick
##

control_room = {
  :width => 6,
  :depth => 6,
  :height => 4,
  :center => Vector.new(-34, 0, 0)
}

hallway = {
  :width => 10,
  :depth => 2,
  :height => 3,
  :center => Vector.new(0, -2, 0),
  :with => [:y, :z]
}

hangar = {
  :width => 20,
  :depth => 30,
  :height => 6,
  :center => Vector.new(62, 0, 0)
}

$panel_id = 0

print "delete from parts; "
def build_part(from_point, to_point)
  query = %|insert into parts (id, construct_id, start_point, end_point, material) values (#{$panel_id}, #{$station_id}, "#{from_point.to_query}", "#{to_point.to_query}", "Steel"); |
  print query
  $panel_id += 1
end

[control_room, hallway, hangar].each do |section|
  sections = section[:with] ||= [:x, :y, :z]
  # Get the parts, and bump them up by 4. Grid is actually 1/4 units, but for simplicity sake
  # the defs above are in full units
  center = section[:center]

  height = section[:height] * 4
  width = section[:width] * 4
  depth = section[:depth] * 4

  half_h = height / 2
  half_w = width / 2
  half_d = depth / 2

  h_wall_size = 1

  if sections.include?(:y)
    # Floor (-y)
    build_part  Vector.new(center.x - half_w, center.y - half_h - 1 - h_wall_size, center.z - half_d),
                Vector.new(center.x + half_w, center.y - half_h - 1 + h_wall_size, center.z + half_d)

    # Ceiling (+y)
    build_part  Vector.new(center.x - half_w, center.y + half_h + 1 - h_wall_size, center.z - half_d),
                Vector.new(center.x + half_w, center.y + half_h + 1 + h_wall_size, center.z + half_d)
  end

  if sections.include?(:x)
    # -x Wall
    build_part  Vector.new(center.x - half_w - 1 - h_wall_size, center.y - half_h, center.z - half_d),
                Vector.new(center.x - half_w - 1 + h_wall_size, center.y + half_h, center.z + half_d)

    # +x Wall
    build_part  Vector.new(center.x + half_w + 1 - h_wall_size, center.y - half_h, center.z - half_d),
                Vector.new(center.x + half_w + 1 + h_wall_size, center.y + half_h, center.z + half_d)
  end

  if sections.include?(:z)
    # +z Wall
    build_part  Vector.new(center.x - half_w, center.y - half_h, center.z - half_d - 1 - h_wall_size),
                Vector.new(center.x + half_w, center.y + half_h, center.z - half_d - 1 + h_wall_size)

    # -z Wall
    build_part  Vector.new(center.x - half_w, center.y - half_h, center.z + half_d + 1 - h_wall_size),
                Vector.new(center.x + half_w, center.y + half_h, center.z + half_d + 1 + h_wall_size)
  end

end
