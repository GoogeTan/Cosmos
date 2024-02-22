package me.katze.cosmos.block.fluid

final class InWorldFluidSource[Stack, Fluid](
                                              block : InWorldFluid[Fluid, Stack], 
                                              emptyStack : Stack
                                            ) extends CountableSource[Stack, Fluid]:
  override def askForExact(amount: Int): Option[Stack] =
    if amount != block.amount then
      None
    else
      askForBucket()
    end if
  end askForExact
  
  override def askForLessThen(amount: Int): Stack =
    if amount < block.amount then
      emptyStack
    else
      askForBucket().getOrElse(emptyStack)
    end if
  end askForLessThen
  
  def askForBucket() : Option[Stack] =
    if block.isSource then
      val res = block.fluidStack
      block.destroy()
      Some(res)
    else
      None
  end askForBucket
  
  override def ingredient: Fluid = block.fluidType
  
  override def toString: String = s"BlockFluidSource($block)"
end InWorldFluidSource
