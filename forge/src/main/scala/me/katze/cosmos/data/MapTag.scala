package me.katze.cosmos.data

import net.minecraft.nbt.{ CompoundTag, Tag }

def MapTag(map : Map[String, Tag]) : CompoundTag =
  val res = CompoundTag()
  map.foreach:
    (k, v) => res.put(k, v)
  res  
end MapTag
