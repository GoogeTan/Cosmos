package me.katze.cosmos.data

import me.katze.cosmos.common.based.data.Ref
import net.minecraft.nbt.{ IntTag, Tag }

final class StrictTagInt(tag : Ref[Tag], orElse : Tag => Int) extends Ref[Int]:
  override def value: Int =
    tag.value match
      case tag: IntTag => tag.getAsInt
      case badTag => orElse(badTag)
    end match
  end value
end StrictTagInt

