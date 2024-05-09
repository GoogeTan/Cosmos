package me.katze.cosmos.data

import me.katze.cosmos.common.based.data.{ MapRef, Ref }
import net.minecraft.core.Registry
import net.minecraft.resources.{ ResourceKey, ResourceLocation }
import net.minecraftforge.registries.IForgeRegistry

def RegistryRef[T](location : Ref[ResourceLocation], registry : IForgeRegistry[T]) : Ref[T] =
  MapRef(location, registry.getValue)
end RegistryRef
