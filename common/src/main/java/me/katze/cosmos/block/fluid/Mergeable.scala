package me.katze.cosmos.block.fluid

trait Mergeable[Fluid, Stack]:
  def isMergeResultEmpty(fluid: Fluid) : Boolean
  def merge(another : Stack) : Stack
end Mergeable