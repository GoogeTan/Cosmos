package me.katze.cosmos.block.fluid

trait LiquidSink[-Source]:
  def tryTakeFrom(source: Source): Unit
end LiquidSink
