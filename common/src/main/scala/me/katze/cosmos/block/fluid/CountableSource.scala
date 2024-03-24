package me.katze.cosmos.block.fluid

trait CountableSource[+Stack, +Ingredient]:
  def askForExact(amount : Int) : Option[Stack]
  def askForLessThen(amount : Int) : Stack
  def ingredient : Ingredient
end CountableSource

