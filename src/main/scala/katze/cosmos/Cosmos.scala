package katze.cosmos

import katze.cosmos.block.Position
import katze.cosmos.block.entity.behaviour.block.{ EmptyInWorldBlock, SidedInWorldBlock, SimpleInWorldBlock }
import katze.cosmos.block.entity.behaviour.fluid.{ FancyFluid, FluidContainerEntityBehaviour, InWorldFluidSource, SimpleInWorldFluid, Tank }
import katze.cosmos.block.entity.behaviour.{ BlockEntityBehaviour, BlockEntityBehaviourFabric }
import katze.cosmos.block.position.Above
import katze.cosmos.data.{ MapRef, Ref }
import katze.cosmos.init.{ EntityBlockRegistrable, IterableRegistrable, RegistryRegistrable }
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
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
class Cosmos:
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
    
    def fluidHopperBehaviourFabric(level : Ref[Level | Null], tag : Ref[Option[CompoundTag]], position : Position, state : Ref[BlockState]): BlockEntityBehaviour =
      val dist = MapRef(level, level => if level.isClientSide then Dist.CLIENT else Dist.DEDICATED_SERVER)
      FluidContainerEntityBehaviour(
        liquidTank = Tank(FancyFluid.empty, 12000),
        inputContainers = List(
          InWorldFluidSource(
            SimpleInWorldFluid(
              SidedInWorldBlock(
                dist,
                SimpleInWorldBlock(level, Above(position)),
                EmptyInWorldBlock
              )
            ),
            FancyFluid.empty
          )
        ),
        outputContainers = List()
      )
    end fluidHopperBehaviourFabric
    
    def entityBlock[B <: BlockEntityBehaviour](name: String, behaviour: BlockEntityBehaviourFabric[B], properties: BlockBehaviour.Properties = BlockBehaviour.Properties.of()): EntityBlockRegistrable[B] =
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
