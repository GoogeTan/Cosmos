package katze.cosmos

import katze.cosmos.block.IntCapacity
import katze.cosmos.block.entity.behaviour.block.{ EmptyInWorldBlock, SidedInWorldBlock, SimpleInWorldBlock }
import katze.cosmos.block.entity.behaviour.fluid.{ FancyFluidStack, SimpleInWorldFluid }
import katze.cosmos.block.entity.behaviour.BlockEntityBehaviourFabric
import katze.cosmos.init.{ EntityBlockRegistrable, IterableRegistrable, RegistryRegistrable }
import me.katze.cosmos.Savable
import me.katze.cosmos.block.{ SingleStackContainerBehaviour, Tickable }
import me.katze.cosmos.block.fluid.{ InWorldFluidSource, Tank }
import me.katze.cosmos.data.{ MapRef, Ref }
import me.katze.cosmos.position.{ BlockAbovePosition, BlockPosition }
import net.minecraft.core.BlockPos
import net.minecraft.nbt.{ CompoundTag, Tag }
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.{ BlockBehaviour, BlockState }
import net.minecraft.world.level.material.MapColor
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

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
    
    def fluidHopperBehaviourFabric(level : Ref[Level | Null], tag : Ref[Option[CompoundTag]], position : BlockPosition, state : Ref[BlockState]) : Tickable with Savable[Tag] =
      val dist = MapRef(level, level => if level.isClientSide then Dist.CLIENT else Dist.DEDICATED_SERVER)
      SingleStackContainerBehaviour(
        stack = Tank(
          FancyFluidStack.empty, 
          IntCapacity(12000),
          (map : Map[String, Tag]) => 
            val res = CompoundTag()
            map.foreach((key, value) => res.put(key, value))
            res
        ),
        input = List(
          InWorldFluidSource(
            SimpleInWorldFluid(
              SidedInWorldBlock(
                dist,
                SimpleInWorldBlock(level, BlockAbovePosition(position)),
                EmptyInWorldBlock
              )
            ),
            FancyFluidStack.empty
          )
        )
      )
    end fluidHopperBehaviourFabric
    
    def entityBlock[
                      B <: Tickable with Savable[Tag]
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
