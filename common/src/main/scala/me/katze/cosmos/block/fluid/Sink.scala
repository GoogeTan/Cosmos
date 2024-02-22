package me.katze.cosmos.block.fluid

trait Sink[-Source]:
  def tryTakeFrom(source: Source): Unit
end Sink
