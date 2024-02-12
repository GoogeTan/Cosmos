package katze.cosmos.data

import net.minecraft.nbt.CompoundTag

trait NbtSerializable:
  def save : CompoundTag
end NbtSerializable
