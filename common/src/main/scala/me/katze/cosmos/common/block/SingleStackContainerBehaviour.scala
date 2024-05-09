package me.katze.cosmos.common.block

import me.katze.cosmos.common.based.data.Ref
import me.katze.cosmos.common.block.InteractionResult.PASS
import me.katze.cosmos.common.based.{ BlockHitResult, Hand, Postmen, Savable, Usable }
import me.katze.cosmos.common.block.fluid.Sink

final class SingleStackContainerBehaviour[
                                            Player <: Postmen,
                                            Storage,
                                            Stack <: Savable[Storage] with Sink[Source],
                                            Source
                                          ](
                                            val isDebug : Ref[Boolean],
                                            val input  : List[Source],
                                            val stack: Stack,
                                            val output : List[Sink[Stack]]
                                          ) extends Tickable with Savable[Storage] with Usable[Player]:
  override def save: Storage =
    stack.save
  end save
  
  override def tick(): Unit =
    input.foreach(stack.tryTakeFrom)
    output.foreach(_.tryTakeFrom(stack))
  end tick
  
  override def use(player: Player, hand: Hand, result: BlockHitResult): InteractionResult =
    if isDebug.value then
      player.sendMessage(this.toString)
    end if
    PASS
  end use
  
  override def toString: String =
    s"SingleStackContainerBehaviour(debug=${isDebug.value};inputs=[${input.mkString(", ")}]; stack=$stack; output=[${output.mkString(", ")}])"
  end toString
end SingleStackContainerBehaviour
