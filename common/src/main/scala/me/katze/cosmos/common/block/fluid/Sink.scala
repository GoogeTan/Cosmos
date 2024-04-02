package me.katze.cosmos.common.block.fluid

trait Sink[-Source]:
  def tryTakeFrom(source: Source): Unit
end Sink
