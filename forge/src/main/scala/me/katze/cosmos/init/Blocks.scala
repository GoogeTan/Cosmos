package me.katze.cosmos.init

import me.katze.cosmos.block.IntCapacity
import me.katze.cosmos.block.entity.behaviour.BlockEntityBehaviourFabric
import me.katze.cosmos.block.entity.behaviour.block.{ EmptyInWorldBlock, SidedInWorldBlock, SimpleInWorldBlock }
import me.katze.cosmos.block.entity.behaviour.fluid.{ FancyFluidStack, SimpleInWorldFluid }
import me.katze.cosmos.common.based.{ Savable, Usable }
import me.katze.cosmos.common.based.data.{ Eager, MapRef, Ref }
import me.katze.cosmos.common.based.data.ref
import me.katze.cosmos.common.based.fluid.InWorldFluidSource
import me.katze.cosmos.common.based.position.{ BlockAbovePosition, BlockPosition }
import me.katze.cosmos.common.block.fluid.Tank
import me.katze.cosmos.common.block.windmill.{ IsRoomFree, WindPower, WindmillBehaviour }
import me.katze.cosmos.common.block.{ SingleStackContainerBehaviour, Tickable }
import me.katze.cosmos.data.{ MapTag, RegistryRef, SubTag, TagInt, TagString }
import me.katze.cosmos.entity.CommonPlayer
import net.minecraft.nbt.{ CompoundTag, Tag }
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.{ BlockBehaviour, BlockState }
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.registries.ForgeRegistries

def TagResourceLocation(tag: Ref[Tag], orElse: => ResourceLocation): Ref[ResourceLocation] =
  MapRef(
    Eager(TagString(tag, _ => orElse.toString)),
    ResourceLocation(_)
  )
end TagResourceLocation

def TagAsFancyFluid(tagRef: Ref[CompoundTag]): FancyFluidStack =
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

final class Blocks(located : String => ResourceLocation):
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
  
  def windmillFabric(level: Ref[Level | Null], tag: Option[Ref[CompoundTag]], position: BlockPosition, state: Ref[BlockState]): Tickable with Savable[Tag] with Usable[CommonPlayer] =
    WindmillBehaviour(
      WindPower(position, IsRoomFree(position, ???)),
      10.ref,
      ???,
      CompoundTag()
    )
  end windmillFabric
  
  def entityBlock[
    B <: Tickable with Savable[Tag] with Usable[CommonPlayer]
  ](
      name: String, behaviour: BlockEntityBehaviourFabric[B],
      properties: BlockBehaviour.Properties = BlockBehaviour.Properties.of()
    ): EntityBlockRegistrable[B] =
    EntityBlockRegistrable(
      located(name),
      properties,
      behaviour
    )
  end entityBlock
  
  def block(name: String, properties: BlockBehaviour.Properties = BlockBehaviour.Properties.of()): RegistryRegistrable[Block] =
    RegistryRegistrable.Block(
      located(name),
      () => new Block(properties)
    )
  end block
end Blocks
