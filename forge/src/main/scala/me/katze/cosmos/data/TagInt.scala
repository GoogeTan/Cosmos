package me.katze.cosmos.data

import me.katze.cosmos.data.Ref
import net.minecraft.nbt.{ NumericTag, Tag }

final class TagInt(tag : Ref[? <: Tag], orElse : Tag => Int) extends Ref[Int]:
  override def value: Int =
    tag.value match
      case tag: NumericTag => tag.getAsInt
      case badTag => orElse(badTag)
    end match
  end value
end TagInt