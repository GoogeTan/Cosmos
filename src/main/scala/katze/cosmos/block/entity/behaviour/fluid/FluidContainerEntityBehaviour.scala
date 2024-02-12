package katze.cosmos.block.entity.behaviour.fluid

import katze.cosmos.block.entity.behaviour.BlockEntityBehaviour
import katze.cosmos.data.NbtSerializable
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component

final class FluidContainerEntityBehaviour[
                                            Tank <: NbtSerializable with LiquidSink[Source],
                                            Source
                                          ](
                                              val name : String = "tank",
                                              val inputContainers  : List[Source], // TODO Отрефакторить в интерфейс получше
                                              val liquidTank: Tank,
                                              val outputContainers : List[LiquidSink[Tank]], // TODO Отрефакторить в интерфейс получше
                                            ) extends BlockEntityBehaviour:
  override def save: CompoundTag =
    val result = CompoundTag()
    result.put(name, liquidTank.save)
    result
  end save
  
  override def tickClient(): Unit =
    Minecraft.getInstance().player.sendSystemMessage(Component.literal(inputContainers.mkString(" ! ")))
  end tickClient
  
  override def tickServer(): Unit =
    inputContainers.foreach(liquidTank.tryTakeFrom)
    outputContainers.foreach(_.tryTakeFrom(liquidTank))
  end tickServer
end FluidContainerEntityBehaviour
