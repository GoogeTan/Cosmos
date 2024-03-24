package me.katze.cosmos.block.entity.behaviour

import me.katze.cosmos.data.Ref
import me.katze.cosmos.position.BlockPosition
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

@FunctionalInterface
trait BlockEntityBehaviourFabric[State]:
  def createBehaviour(level : Ref[Level | Null], tag : Option[Ref[CompoundTag]], position : BlockPosition, state : Ref[BlockState]) : State
end BlockEntityBehaviourFabric
