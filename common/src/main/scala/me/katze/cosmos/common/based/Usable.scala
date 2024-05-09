package me.katze.cosmos.common.based

import me.katze.cosmos.common.block.InteractionResult

trait Usable[-Player]:
  def use(player : Player, hand : Hand, result: BlockHitResult) : InteractionResult
end Usable

