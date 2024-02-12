package katze.cosmos.block.entity.behaviour.fluid

import katze.cosmos.block.entity.behaviour.fluid.FancyFluid.MergeResultFluid
import katze.cosmos.data.*
import katze.cosmos.data.number.Sum
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.material.{ Fluid, FluidState, Fluids }

final case class FancyFluid(
                              fluidType: Ref[Fluid],
                              amountIn : Ref[Int]
                            ) extends Countable with NbtSerializable with Mergeable[Fluid, FancyFluid]:
  override def amount: Int = amountIn.value
  override def save: CompoundTag = ???
  def isEmpty : Boolean = amountIn.value == 0 || fluidType.value == Fluids.EMPTY
  
  override def isMergeResultEmpty(fluid: Fluid): Boolean =
    AreSame(
      MergeResultFluid(this.fluidType, FunctionRef(() => fluid)),
      FunctionRef(() => Fluids.EMPTY)
    )(using Equiv.universal).value
  end isMergeResultEmpty
  
  override def merge(another: FancyFluid): FancyFluid =
    new FancyFluid(
      Conditional(
        AreSame(fluidType, another.fluidType)(using fluidsAreMergeable),
        MergeResultFluid(fluidType, another.fluidType),
        FunctionRef(() => Fluids.EMPTY)
      ),
      Sum(amountIn, another.amountIn)
    )
  end merge
  
  private def fluidsAreMergeable : Equiv[Fluid] = (a, b) => a == Fluids.EMPTY || b == Fluids.EMPTY || a.isSame(b)
end FancyFluid

object FancyFluid:
  
  final class MergeResultFluid(first : Ref[Fluid], second : Ref[Fluid]) extends Ref[Fluid]:
    override def value: Fluid =
      if first.value == Fluids.EMPTY then
        second.value
      else if second.value == Fluids.EMPTY then
        first.value
      else
        assert(first.value.isSame(second.value))
        first.value
      end if
    end value
  end MergeResultFluid
  
  val empty : FancyFluid = FancyFluid(Fluids.EMPTY, 0)
  
  def apply(typeIn : Fluid, amount : Int) : FancyFluid = new FancyFluid(FunctionRef(() => typeIn), FunctionRef(() => amount))
  
  def fromState(fluidState: FluidState) : FancyFluid = FancyFluid(fluidState.getType, fluidState.getAmount)
end FancyFluid
