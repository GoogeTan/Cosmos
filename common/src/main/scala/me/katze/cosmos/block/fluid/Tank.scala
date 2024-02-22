package me.katze.cosmos.block.fluid

import me.katze.cosmos.Savable

final class Tank[ 
                  Tag,
                  Fluid,
                  FluidStack <: Countable & Savable[Tag] & Mergeable[Fluid, FluidStack],
                ](
                    private var stack : FluidStack,
                    maxCapacity : Capacity with Savable[Tag],
                    compoundTag : Map[String, Tag] => Tag
                  ) extends Sink[CountableSource[FluidStack, Fluid]] with Savable[Tag]:
  override def tryTakeFrom(source: CountableSource[FluidStack, Fluid]): Unit =
    if !stack.isMergeResultEmpty(source.ingredient) then
      this.stack = stack.merge(source.askForLessThen(freeAmount))
    end if
  end tryTakeFrom
  
  def freeAmount : Int = maxCapacity.remains(stack.amount)
  
  override def save: Tag =
    compoundTag(
      Map(
        "stack" -> stack.save,
        "capacity" -> maxCapacity.save
      )
    )
  end save
  
  override def toString: String = s"Tank(capacity=$maxCapacity;stack=$stack)"
end Tank

