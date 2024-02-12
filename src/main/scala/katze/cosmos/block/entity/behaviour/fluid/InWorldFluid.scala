package katze.cosmos.block.entity.behaviour.fluid

import katze.cosmos.block.entity.behaviour.Destroyable

trait InWorldFluid[FluidType, FluidStack] extends Destroyable:
  def isSource : Boolean
  def fluidType : FluidType
  def fluidStack : FluidStack
  def amount : Int
end InWorldFluid