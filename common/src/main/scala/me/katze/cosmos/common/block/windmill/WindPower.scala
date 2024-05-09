package me.katze.cosmos.common.block.windmill

import me.katze.cosmos.common.based.data.{ ConditionalRef, FunctionRef, Ref, ref }
import me.katze.cosmos.common.based.position.{ BlockPosition, RelativePosition }

def WindPower(at : BlockPosition, isRoomFree : Ref[Boolean]) : Ref[Int] =
  ConditionalRef[Int](isRoomFree, at.y.ref, 0.ref)
end WindPower

trait AirSpace:
  def isAir(at : BlockPosition) : Boolean
end AirSpace

// TODO
def IsRoomFree(at : BlockPosition, airSpace : AirSpace) : Ref[Boolean] =
  FunctionRef(() => {
    var res = 0
    for
      i <- -5 to 5
      j <- -5 to 5
      k <- -5 to 5
      if airSpace.isAir(RelativePosition(at, i, j, k))
    do res += 1
    res > ((-5 to 5).length ^ 3 / 2)
  })
end IsRoomFree
