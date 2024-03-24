package me.katze.cosmos.block.fluid

trait Capacity:
  def remains(taken : Int) : Int
end Capacity

