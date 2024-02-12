package katze.cosmos.block.entity.behaviour.fluid

trait FancyFluidStack:
  type Fluid
  
  def fluidType : Fluid
  def amount : Int
end FancyFluidStack

