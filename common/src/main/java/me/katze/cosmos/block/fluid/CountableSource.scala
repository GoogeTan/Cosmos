package me.katze.cosmos.block.fluid

trait CountableSource[Stack, Fluid]:
  def askForExact(amount : Int) : Option[Stack]
  def askForLessThen(amount : Int) : Stack
  def fluidType : Fluid
end CountableSource

