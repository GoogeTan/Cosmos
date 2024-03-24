package me.katze.cosmos.block

import me.katze.cosmos.Savable
import me.katze.cosmos.block.fluid.Sink

final class SingleStackContainerBehaviour[
                                            Storage,
                                            Stack <: Savable[Storage] with Sink[Source],
                                            Source
                                          ](
                                            val input  : List[Source],
                                            val stack: Stack,
                                            val output : List[Sink[Stack]]
                                          ) extends Tickable with Savable[Storage]:
  override def save: Storage =
    stack.save
  end save
  
  override def tick(): Unit =
    input.foreach(stack.tryTakeFrom)
    output.foreach(_.tryTakeFrom(stack))
  end tick
end SingleStackContainerBehaviour
