package me.katze.cosmos.block.entity.behaviour.block

import me.katze.cosmos.common.based.Destroyable
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState

trait InWorldBlock extends Destroyable:
  def blockState : BlockState
  def fluidState : FluidState
end InWorldBlock
