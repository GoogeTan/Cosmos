package katze.cosmos.block.entity.behaviour.fluid

import katze.cosmos.block.entity.behaviour.block.InWorldBlock
import net.minecraft.world.level.material.Fluid

final class SimpleInWorldFluid(block : InWorldBlock) extends InWorldFluid[Fluid, FancyFluid]:
  override def fluidStack: FancyFluid = FancyFluid.fromState(block.fluidState)
  
  override def fluidType: Fluid = fluidStack.fluidType.value
  
  override def destroy(): Unit = block.destroy()
  
  override def amount: Int = fluidStack.amount
  
  override def isSource: Boolean = block.fluidState.isSource
end SimpleInWorldFluid

