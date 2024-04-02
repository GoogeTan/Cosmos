package me.katze.cosmos.block.entity.behaviour.fluid

import FancyFluidStack.emptyFluidRef
import me.katze.cosmos.common.Savable
import me.katze.cosmos.data.MapTag
import me.katze.cosmos.common.block.fluid.{ Countable, Mergeable }
import me.katze.cosmos.common.data.{ AreSame, ConditionalRef, FunctionRef, Ref }
import me.katze.cosmos.common.data.number.Sum
import net.minecraft.nbt.{ CompoundTag, IntTag, StringTag }
import net.minecraft.world.level.material.{ Fluid, FluidState, Fluids }
import net.minecraftforge.registries.ForgeRegistries

final case class FancyFluidStack(
                                  fluidType: Ref[Fluid],
                                  amountIn : Ref[Int]
                                ) extends Countable with Savable[CompoundTag] with Mergeable[Fluid, FancyFluidStack]:
  override def amount: Int = amountIn.value
  
  override def save: CompoundTag =
    MapTag(
      Map(
        "fluid" -> StringTag.valueOf(ForgeRegistries.FLUIDS.getKey(fluidType.value).toString),
        "amount" -> IntTag.valueOf(amountIn.value),
      )
    )
  end save
  
  def isEmpty : Boolean = amountIn.value == 0 || fluidType.value == Fluids.EMPTY
  
  override def isMergeResultEmpty(fluid: Fluid): Boolean =
    AreSame(
      FancyFluidStackMergeResultFluid(this.fluidType, FunctionRef(() => fluid)),
      emptyFluidRef
    )(using Equiv.universal).value
  end isMergeResultEmpty
  
  override def merge(another: FancyFluidStack): FancyFluidStack =
    new FancyFluidStack(
      ConditionalRef(
        inCase = AreSame(fluidType, another.fluidType)(using fluidsAreMergeable),
        use = FancyFluidStackMergeResultFluid(fluidType, another.fluidType),
        otherwise = emptyFluidRef
      ),
      Sum(amountIn, another.amountIn)
    )
  end merge
  
  private def fluidsAreMergeable : Equiv[Fluid] = (a, b) => a == Fluids.EMPTY || b == Fluids.EMPTY || a.isSame(b)
  
  override def toString: String = s"FFluidStack(fluid=${ForgeRegistries.FLUIDS.getKey(fluidType.value).toString};amount=${amountIn.value})"
end FancyFluidStack

object FancyFluidStack:
  private val emptyFluidRef : Ref[Fluid] = FunctionRef(() => Fluids.EMPTY)
  
  val empty : FancyFluidStack = FancyFluidStack(Fluids.EMPTY, 0)
  
  def apply(typeIn : Fluid, amount : Int) : FancyFluidStack = new FancyFluidStack(FunctionRef(() => typeIn), FunctionRef(() => amount))
  
  def fromState(fluidState: FluidState) : FancyFluidStack = FancyFluidStack(fluidState.getType, fluidState.getAmount)
end FancyFluidStack
