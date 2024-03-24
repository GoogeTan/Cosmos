package me.katze.cosmos.block.entity.behaviour.block

import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState

object EmptyInWorldBlock extends InWorldBlock:
  override lazy val blockState: BlockState = Blocks.AIR.defaultBlockState()
  override lazy val fluidState: FluidState = blockState.getFluidState
  override def destroy(): Unit = ()
end EmptyInWorldBlock

