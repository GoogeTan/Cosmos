package me.katze.cosmos.position

final class BlockAbovePosition(original : BlockPosition) extends BlockPosition:
  override def x: Int = original.x
  
  override def y: Int = original.y + 1
  
  override def z: Int = original.x
end BlockAbovePosition
