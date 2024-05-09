package me.katze.cosmos

import me.katze.cosmos.block.entity.behaviour.BlockEntityBehaviourFabric
import me.katze.cosmos.common.block.Tickable
import me.katze.cosmos.common.based.{ Savable, Usable }
import me.katze.cosmos.entity.CommonPlayer
import me.katze.cosmos.init.{ Blocks, Items, IterableRegistrable, RegistryRegistrable }
import net.minecraft.nbt.Tag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

@Mod("cosmos")
final class Cosmos:
  private val blocks = Blocks(ResourceLocation("cosmos", _))
  private val items = Items(ResourceLocation("cosmos", _))
  
  private val modBlocks: IterableRegistrable = IterableRegistrable(
    List(
      blocks.entityBlock("liquid_hopper", blocks.fluidHopperBehaviourFabric),
      blocks.entityBlock("windmill", blocks.windmillFabric),
      blocks.block("example", BlockBehaviour.Properties.of.mapColor(MapColor.STONE))
    )
  )
  private val modItems: IterableRegistrable = IterableRegistrable(
    List(
      items.blockItem("liquid_hopper"),
      items.blockItem("example"),
      
      items.item("copper_wire"),
      items.item("copper_coil"),
    )
  )
  
  {
    val modEventBus: IEventBus = FMLJavaModLoadingContext.get.getModEventBus
    modBlocks.registerAt(modEventBus)
    modItems.registerAt(modEventBus)
  }
end Cosmos
