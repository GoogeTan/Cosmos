package me.katze.cosmos.block

import me.katze.cosmos.common.Savable
import me.katze.cosmos.common.block.fluid.Capacity
import net.minecraft.nbt.IntTag

final class IntCapacity(maxValue : Int) extends Capacity with Savable[IntTag]:
  override def remains(taken: Int): Int = maxValue - taken
  
  override def save: IntTag = IntTag.valueOf(maxValue)
  
  override def toString: String = maxValue.toString
end IntCapacity
