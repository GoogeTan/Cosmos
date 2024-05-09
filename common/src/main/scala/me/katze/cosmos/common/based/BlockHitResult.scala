package me.katze.cosmos.common.based

import me.katze.cosmos.common.based.position.{ BlockPosition, EntityPosition }
import me.katze.cosmos.common.block.{ HitResult, HitType }

case class BlockHitResult(
                            miss: Boolean,
                            location : EntityPosition,
                            direction: Direction,
                            blockPos: BlockPosition,
                            inside: Boolean
                          ):
  def this(location : EntityPosition, direction : Direction, blockPos : BlockPosition, inside : Boolean) =
    this(false, location, direction, blockPos, inside)
  
  def withDirection(newDirection : Direction): BlockHitResult =
    BlockHitResult(this.miss, this.location, newDirection, this.blockPos, this.inside)
  end withDirection
  
  def withPosition(newPosition : BlockPosition): BlockHitResult =
    BlockHitResult(this.miss, this.location, this.direction, newPosition, this.inside)
  end withPosition
  
  def hitResult : HitResult =
    HitResult(
      location,
      if this.miss then HitType.MISS else HitType.BLOCK
    )
  end hitResult
end BlockHitResult
