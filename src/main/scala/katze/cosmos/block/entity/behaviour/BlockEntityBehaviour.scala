package katze.cosmos.block.entity.behaviour

import net.minecraft.nbt.CompoundTag

trait BlockEntityBehaviour:
  def tickServer(): Unit
  
  def tickClient() : Unit
  
  def save : CompoundTag
end BlockEntityBehaviour

