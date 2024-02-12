package katze.cosmos.block.entity.behaviour.fluid

import katze.cosmos.data.NbtSerializable
import net.minecraft.nbt.CompoundTag

import java.util.logging.Logger

final class Tank[
                  Fluid,
                  FluidStack <: Countable & NbtSerializable & Mergeable[Fluid, FluidStack],
                ](
                    private var stack : FluidStack,
                    maxCapacity : Int
                ) extends LiquidSink[CountableSource[FluidStack, Fluid]] with NbtSerializable:
  override def tryTakeFrom(source: CountableSource[FluidStack, Fluid]): Unit =
    if !stack.isMergeResultEmpty(source.fluidType) then
      return
    val taken = source.askForLessThen(freeAmount)
    val newStack = stack.merge(taken)
    if stack != newStack then
      Tank.logger.warning(s"Trying to take from. $stack $newStack")
    this.stack = newStack
  end tryTakeFrom
  
  def freeAmount : Int = maxCapacity - stack.amount
  
  override def save: CompoundTag = stack.save
  
  override def toString: String = s"Tank(max=$maxCapacity;stack=$stack)"
end Tank

object Tank:
  private val logger = Logger.getLogger("Tank")
end Tank
