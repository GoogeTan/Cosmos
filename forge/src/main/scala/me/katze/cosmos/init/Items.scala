package me.katze.cosmos.init

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.{ BlockItem, Item }
import net.minecraftforge.registries.ForgeRegistries

final class Items(located : String => ResourceLocation):
  def blockItem(name: String, properties: Item.Properties = Item.Properties()): RegistryRegistrable[Item] =
    val location = located(name)
    RegistryRegistrable.Item(
      location,
      () => new BlockItem(ForgeRegistries.BLOCKS.getValue(location), properties)
    )
  end blockItem
  
  def item(name: String, properties: Item.Properties = Item.Properties()): RegistryRegistrable[Item] =
    RegistryRegistrable.Item(
      located(name),
      () => new Item(properties)
    )
  end item
end Items
