package me.katze.cosmos.block.fluid

import me.katze.cosmos.Savable

// TODO Доделать сереализацию
final class Tank[ 
                  CompoundTag,
                  Fluid,
                  FluidStack <: Countable & Savable[CompoundTag] & Mergeable[Fluid, FluidStack],
                ](
                    private var stack : FluidStack,
                    maxCapacity : Int
                ) extends LiquidSink[CountableSource[FluidStack, Fluid]] with Savable[CompoundTag]:
  override def tryTakeFrom(source: CountableSource[FluidStack, Fluid]): Unit =
    if !stack.isMergeResultEmpty(source.fluidType) then
      return
    end if
    this.stack = stack.merge(source.askForLessThen(freeAmount))
  end tryTakeFrom
  
  def freeAmount : Int = maxCapacity - stack.amount
  
  override def save: CompoundTag = stack.save
  
  override def toString: String = s"Tank(max=$maxCapacity;stack=$stack)"
end Tank

