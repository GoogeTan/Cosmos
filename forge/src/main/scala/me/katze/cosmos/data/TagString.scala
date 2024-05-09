package me.katze.cosmos.data

import me.katze.cosmos.common.based.data.Ref
import net.minecraft.nbt.{ StringTag, Tag }

final class TagString(tag : Ref[? <: Tag], fallback : Tag => String) extends Ref[String]:
  override def value: String =
    tag.value match
      case tag: StringTag => tag.getAsString
      case another => fallback(another) 
    end match
  end value
end TagString
