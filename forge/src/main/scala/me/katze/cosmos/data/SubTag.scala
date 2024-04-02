package me.katze.cosmos.data

import me.katze.cosmos.common.data.{ MapRef, Ref }
import net.minecraft.nbt.{ CompoundTag, Tag }

def SubTag(ref : Ref[CompoundTag], key : Ref[String] | String) : Ref[Tag | Null] =
  MapRef[CompoundTag, Tag | Null](
    ref,
    tag =>
      key match
        case s : String => tag.get(s)
        case r : Ref[String] => tag.get(r.value)
      end match
  )
end SubTag
