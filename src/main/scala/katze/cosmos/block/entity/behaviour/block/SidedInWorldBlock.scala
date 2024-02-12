package katze.cosmos.block.entity.behaviour.block

import katze.cosmos.data.Ref
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState
import net.minecraftforge.api.distmarker.Dist

final class SidedInWorldBlock(side : Ref[Dist], server : InWorldBlock, client : InWorldBlock) extends InWorldBlock:
  override def fluidState: FluidState = side.value match
    case Dist.CLIENT => client.fluidState
    case Dist.DEDICATED_SERVER => server.fluidState
  end fluidState
  
  override def blockState: BlockState = side.value match
    case Dist.CLIENT => client.blockState
    case Dist.DEDICATED_SERVER => server.blockState
  end blockState
  
  override def destroy(): Unit = side.value match
    case Dist.CLIENT => client.destroy()
    case Dist.DEDICATED_SERVER => server.destroy()
  end destroy
end SidedInWorldBlock

