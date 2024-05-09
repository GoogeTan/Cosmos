package me.katze.cosmos.block.entity.behaviour.fluid

import me.katze.cosmos.block.entity.behaviour.block.InWorldBlock
import me.katze.cosmos.common.based.fluid.InWorldFluid
import net.minecraft.world.level.material.Fluid

final class SimpleInWorldFluid(block : InWorldBlock) extends InWorldFluid[Fluid, FancyFluidStack]:
  override def fluidStack: FancyFluidStack = FancyFluidStack.fromState(block.fluidState)
  
  override def fluidType: Fluid = fluidStack.fluidType.value
  
  override def destroy(): Unit = block.destroy()
  
  override def amount: Int = fluidStack.amount
  
  override def isSource: Boolean = block.fluidState.isSource
  
  override def toString: String = s"SimpleInWorldFluid(${block.toString})"
end SimpleInWorldFluid

