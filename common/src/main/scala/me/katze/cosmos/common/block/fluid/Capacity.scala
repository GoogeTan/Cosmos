package me.katze.cosmos.common.block.fluid

trait Capacity:
  def remains(taken : Int) : Int
end Capacity

