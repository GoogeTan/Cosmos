package me.katze.cosmos.common.block.fluid

import me.katze.cosmos.common.based.Savable
import me.katze.cosmos.common.based.fluid.{ Capacity, Countable, CountableSource, Mergeable }

final class Tank[ 
                  Tag,
                  Fluid,
                  FluidStack <: Countable & Savable[Tag] & Mergeable[Fluid, FluidStack],
                ](
                    private var stack : FluidStack,
                    capacity : Capacity with Savable[Tag],
                    compoundTag : Map[String, Tag] => Tag
                  ) extends Sink[CountableSource[FluidStack, Fluid]] with Savable[Tag]:
  override def tryTakeFrom(source: CountableSource[FluidStack, Fluid]): Unit =
    if !stack.isMergeResultEmpty(source.ingredient) then
      this.stack = stack.merge(source.askForLessThen(freeAmount))
    end if
  end tryTakeFrom
  
  def freeAmount : Int = capacity.remains(stack.amount)
  
  override def save: Tag =
    compoundTag(
      Map(
        "stack" -> stack.save,
        "capacity" -> capacity.save
      )
    )
  end save
  
  override def toString: String = s"Tank(capacity=${capacity.toString};stack=${stack.toString})"
end Tank

