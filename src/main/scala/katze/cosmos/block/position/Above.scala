package katze.cosmos.block.position

import katze.cosmos.block.Position

final class Above(pos : Position) extends Position:
  override def x: Int = pos.x
  
  override def y: Int = pos.y + 1
  
  override def z: Int = pos.z
end Above
