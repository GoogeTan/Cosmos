package me.katze.cosmos

import me.katze.cosmos.block.IntCapacity
import me.katze.cosmos.block.entity.behaviour.BlockEntityBehaviourFabric
import me.katze.cosmos.block.entity.behaviour.block.{ EmptyInWorldBlock, SidedInWorldBlock, SimpleInWorldBlock }
import me.katze.cosmos.block.entity.behaviour.fluid.{ FancyFluidStack, SimpleInWorldFluid }
import me.katze.cosmos.common.block.fluid.{ InWorldFluidSource, Tank }
import me.katze.cosmos.common.block.{ SingleStackContainerBehaviour, Tickable }
import me.katze.cosmos.common.data.{ Eager, FunctionRef, MapRef, Ref }
import me.katze.cosmos.common.position.{ BlockAbovePosition, BlockPosition }
import me.katze.cosmos.common.{ Savable, Usable }
import me.katze.cosmos.data.*
import me.katze.cosmos.entity.CommonPlayer
import me.katze.cosmos.init.{ EntityBlockRegistrable, IterableRegistrable, RegistryRegistrable }
import net.minecraft.nbt.{ CompoundTag, Tag }
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.{ BlockBehaviour, BlockState }
import net.minecraft.world.level.material.{ Fluid, MapColor }
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.ForgeRegistries

//noinspection ScalaUnusedSymbol
@Mod("cosmos")
final class Cosmos:
  private val modBlocks: IterableRegistrable = IterableRegistrable(
    List(
      Blocks.entityBlock("liquid_hopper", Blocks.fluidHopperBehaviourFabric),
      Blocks.block("example", BlockBehaviour.Properties.of.mapColor(MapColor.STONE))
    )
  )
  
  {
    val modEventBus: IEventBus = FMLJavaModLoadingContext.get.getModEventBus
    modBlocks.registerAt(modEventBus)
  }
  
  object Blocks:
    def location(path : String) : ResourceLocation = ResourceLocation("cosmos", path)
    
    def TagResourceLocation(tag : Ref[Tag], orElse : => ResourceLocation) : Ref[ResourceLocation] =
      MapRef(
        Eager(TagString(tag, _ => orElse.toString)),
        ResourceLocation(_)
      )
    end TagResourceLocation
    
    def TagAsFancyFluid(tagRef : Ref[CompoundTag]) : FancyFluidStack =
      FancyFluidStack(
        Eager(
          RegistryRef[Fluid](
            TagResourceLocation(
              tag = SubTag(tagRef, "fluid"),
              orElse = ForgeRegistries.FLUIDS.getDefaultKey
            ),
            registry = ForgeRegistries.FLUIDS
          )
        ),
        Eager(
          TagInt(
            tag = SubTag(tagRef, "amount"), 
            orElse = _ => 0
          )
        )
      )
    end TagAsFancyFluid
    
    def fluidHopperBehaviourFabric(level : Ref[Level | Null], tag : Option[Ref[CompoundTag]], position : BlockPosition, state : Ref[BlockState]) : Tickable with Savable[Tag] with Usable[CommonPlayer] =
      val dist = MapRef(level, level => if level.isClientSide then Dist.CLIENT else Dist.DEDICATED_SERVER)
      SingleStackContainerBehaviour(
        isDebug = MapRef(dist, d => d == Dist.DEDICATED_SERVER),
        stack = Tank(
          tag.map(TagAsFancyFluid).getOrElse(FancyFluidStack.empty),
          IntCapacity(12000),
          MapTag
        ),
        input = List(
          InWorldFluidSource(
            SimpleInWorldFluid(
              SidedInWorldBlock(
                dist,
                server = SimpleInWorldBlock(level, BlockAbovePosition(position)),
                client = EmptyInWorldBlock
              )
            ),
            FancyFluidStack.empty
          )
        ),
        output = List()
      )
    end fluidHopperBehaviourFabric
    
    def entityBlock[
                      B <: Tickable with Savable[Tag] with Usable[CommonPlayer]
                    ](
                        name: String, behaviour: BlockEntityBehaviourFabric[B],
                        properties: BlockBehaviour.Properties = BlockBehaviour.Properties.of()
                      ): EntityBlockRegistrable[B] =
      EntityBlockRegistrable(
        location(name),
        properties, 
        behaviour
      )
    end entityBlock
    
    def block(name: String, properties: BlockBehaviour.Properties = BlockBehaviour.Properties.of()): RegistryRegistrable[Block] =
      RegistryRegistrable.Block(
        location(name),
        () => new Block(properties)
      )
    end block
  end Blocks
end Cosmos
