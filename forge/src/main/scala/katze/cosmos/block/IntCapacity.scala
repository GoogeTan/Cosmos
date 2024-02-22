package katze.cosmos.block

import me.katze.cosmos.Savable
import me.katze.cosmos.block.fluid.Capacity
import net.minecraft.nbt.IntTag

final class IntCapacity(maxValue : Int) extends Capacity with Savable[IntTag]:
  override def remains(amount: Int): Int = maxValue - amount
  
  override def save: IntTag = IntTag.valueOf(maxValue)
end IntCapacity