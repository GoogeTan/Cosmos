package me.katze.cosmos.common.based.fluid

import me.katze.cosmos.common.based.Destroyable

trait InWorldFluid[FluidType, FluidStack] extends Destroyable:
  def isSource : Boolean
  def fluidType : FluidType
  def fluidStack : FluidStack
  def amount : Int
end InWorldFluid
