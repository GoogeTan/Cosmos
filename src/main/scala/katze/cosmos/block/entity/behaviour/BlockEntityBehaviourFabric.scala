package katze.cosmos.block.entity.behaviour

import katze.cosmos.block.Position
import katze.cosmos.data.Ref
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

trait BlockEntityBehaviourFabric[State]:
  def createBehaviour(level : Ref[Level | Null], tag : Ref[Option[CompoundTag]], position : Position, state : Ref[BlockState]) : State
end BlockEntityBehaviourFabric
