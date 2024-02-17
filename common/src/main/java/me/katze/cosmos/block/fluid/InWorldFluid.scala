package me.katze.cosmos.block.fluid

import me.katze.cosmos.Destroyable

trait InWorldFluid[FluidType, FluidStack] extends Destroyable:
  def isSource : Boolean
  def fluidType : FluidType
  def fluidStack : FluidStack
  def amount : Int
end InWorldFluid