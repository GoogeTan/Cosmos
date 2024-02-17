package katze.cosmos

import katze.cosmos.block.entity.behaviour.block.{ EmptyInWorldBlock, SidedInWorldBlock, SimpleInWorldBlock }
import katze.cosmos.block.entity.behaviour.fluid.{ FancyFluidStack, SimpleInWorldFluid }
import katze.cosmos.block.entity.behaviour.BlockEntityBehaviourFabric
import katze.cosmos.init.{ EntityBlockRegistrable, IterableRegistrable, RegistryRegistrable }
import me.katze.cosmos.Savable
import me.katze.cosmos.block.{ BlockEntityBehaviour, FluidContainerEntityBehaviour }
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
    
    def fluidHopperBehaviourFabric(level : Ref[Level | Null], tag : Ref[Option[CompoundTag]], position : BlockPosition, state : Ref[BlockState]) : BlockEntityBehaviour with Savable[CompoundTag] =
      val dist = MapRef(level, level => if level.isClientSide then Dist.CLIENT else Dist.DEDICATED_SERVER)
      FluidContainerEntityBehaviour(
        liquidTank = Tank(FancyFluidStack.empty, 12000),
        inputContainers = List(
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
        ),
        outputContainers = List()
      )
    end fluidHopperBehaviourFabric
    
    def entityBlock[
                      B <: BlockEntityBehaviour with Savable[CompoundTag]
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
