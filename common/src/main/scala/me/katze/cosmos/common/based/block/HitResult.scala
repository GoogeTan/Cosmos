package me.katze.cosmos.common.based.block

import me.katze.cosmos.common.based.position.EntityPosition

case class HitResult(location: EntityPosition, hitType : HitType):
  def distanceTo(anotherPosition : EntityPosition): Double =
    val dx = this.location.x - anotherPosition.x
    val dy = this.location.y - anotherPosition.y
    val dz = this.location.z - anotherPosition.z
    dx * dx + dy * dy + dz * dz
  end distanceTo
end HitResult