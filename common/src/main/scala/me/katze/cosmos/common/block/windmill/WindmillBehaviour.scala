package me.katze.cosmos.common.block.windmill

import me.katze.cosmos.common.based.{ BlockHitResult, Hand, Savable, Usable }
import me.katze.cosmos.common.based.data.Ref
import me.katze.cosmos.common.based.data.number.{ *, given }
import me.katze.cosmos.common.block.{ InteractionResult, Tickable }


type Energy = Ref[Int]

trait EnergyOutput:
  def recieveEnery(energy : Energy) : Unit
end EnergyOutput

final class WindmillBehaviour[T](
                                  windPower : Ref[Int], 
                                  efficiency : Ref[Int],
                                  energyOutput : EnergyOutput,
                                  nothingToSave : T
                                ) extends Tickable with Savable[T] with Usable[Any]:
  override def tick(): Unit =
    energyOutput.recieveEnery(windPower * efficiency)
  end tick
  
  override def save: T = nothingToSave
  
  override def use(player: Any, hand: Hand, result: BlockHitResult): InteractionResult = InteractionResult.PASS
end WindmillBehaviour
