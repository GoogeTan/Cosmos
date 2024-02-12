package katze.cosmos.block.entity.behaviour.fluid

trait LiquidSink[-Source]:
  def tryTakeFrom(source: Source): Unit
end LiquidSink
