package me.katze.cosmos.block

import me.katze.cosmos.Savable
import me.katze.cosmos.block.fluid.LiquidSink

final class FluidContainerEntityBehaviour[
                                            CompoundTag,
                                            Tank <: Savable[CompoundTag] with LiquidSink[Source],
                                            Source
                                          ](
                                              val name : String = "tank",
                                              val inputContainers  : List[Source], // TODO Отрефакторить в интерфейс получше
                                              val liquidTank: Tank,
                                              val outputContainers : List[LiquidSink[Tank]], // TODO Отрефакторить в интерфейс получше
                                            ) extends BlockEntityBehaviour with Savable[CompoundTag]:
  override def save: CompoundTag =
    liquidTank.save
  end save
  
  override def tickClient(): Unit =
    ()
  end tickClient
  
  override def tickServer(): Unit =
    inputContainers.foreach(liquidTank.tryTakeFrom)
    outputContainers.foreach(_.tryTakeFrom(liquidTank))
  end tickServer
end FluidContainerEntityBehaviour
