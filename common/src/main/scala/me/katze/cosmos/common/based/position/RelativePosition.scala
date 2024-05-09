package me.katze.cosmos.common.based.position

final class RelativePosition(pos : BlockPosition, dx : Int, dy : Int, dz : Int) extends BlockPosition:
  override def x: Int = pos.x + dx
  
  override def y: Int = pos.y + dy
  
  override def z: Int = pos.z + dz
end RelativePosition

