package katze.cosmos.block.entity.behaviour.block

import me.katze.cosmos.data.Ref
import me.katze.cosmos.position.BlockPosition
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState

final class SimpleInWorldBlock(level : Ref[Level], pos : BlockPosition) extends InWorldBlock:
  override def fluidState: FluidState = blockState.getFluidState
  override def blockState: BlockState = level.value.getBlockState(new BlockPos(pos.x, pos.y, pos.z))
  override def destroy(): Unit =
    level.value.setBlockAndUpdate(new BlockPos(pos.x, pos.y, pos.z), Blocks.AIR.defaultBlockState())
  end destroy
  
  override def toString: String = s"SimpleInWorldBlock(level=${level.value.dimension()};position=$pos;fluidState=FluidState(fluid=${fluidState.getType};amount=${fluidState.getAmount}))"
end SimpleInWorldBlock