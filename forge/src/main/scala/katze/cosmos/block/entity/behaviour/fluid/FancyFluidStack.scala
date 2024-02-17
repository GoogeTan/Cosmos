package katze.cosmos.block.entity.behaviour.fluid

import katze.cosmos.block.entity.behaviour.fluid.FancyFluidStack.emptyFluidRef
import me.katze.cosmos.Savable
import me.katze.cosmos.block.fluid.{ Countable, Mergeable }
import me.katze.cosmos.data.number.Sum
import me.katze.cosmos.data.{ AreSame, Conditional, FunctionRef, Ref }
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.material.{ Fluid, FluidState, Fluids }

final case class FancyFluidStack(
                                  fluidType: Ref[Fluid],
                                  amountIn : Ref[Int]
                                ) extends Countable with Savable[CompoundTag] with Mergeable[Fluid, FancyFluidStack]:
  override def amount: Int = amountIn.value
  override def save: CompoundTag = ???
  def isEmpty : Boolean = amountIn.value == 0 || fluidType.value == Fluids.EMPTY
  
  override def isMergeResultEmpty(fluid: Fluid): Boolean =
    AreSame(
      FancyFluidStackMergeResultFluid(this.fluidType, FunctionRef(() => fluid)),
      emptyFluidRef
    )(using Equiv.universal).value
  end isMergeResultEmpty
  
  override def merge(another: FancyFluidStack): FancyFluidStack =
    new FancyFluidStack(
      Conditional(
        AreSame(fluidType, another.fluidType)(using fluidsAreMergeable),
        FancyFluidStackMergeResultFluid(fluidType, another.fluidType),
        emptyFluidRef
      ),
      Sum(amountIn, another.amountIn)
    )
  end merge
  
  private def fluidsAreMergeable : Equiv[Fluid] = (a, b) => a == Fluids.EMPTY || b == Fluids.EMPTY || a.isSame(b)
end FancyFluidStack

object FancyFluidStack:
  private val emptyFluidRef : Ref[Fluid] = FunctionRef(() => Fluids.EMPTY)
  
  val empty : FancyFluidStack = FancyFluidStack(Fluids.EMPTY, 0)
  
  def apply(typeIn : Fluid, amount : Int) : FancyFluidStack = new FancyFluidStack(FunctionRef(() => typeIn), FunctionRef(() => amount))
  
  def fromState(fluidState: FluidState) : FancyFluidStack = FancyFluidStack(fluidState.getType, fluidState.getAmount)
end FancyFluidStack
