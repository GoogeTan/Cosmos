package me.katze.cosmos.block.entity

import me.katze.cosmos.Savable
import me.katze.cosmos.block.Tickable
import me.katze.cosmos.block.entity.behaviour.BlockEntityBehaviourFabric
import me.katze.cosmos.data.{ FunctionRef, Ref }
import me.katze.cosmos.position.BlockPosition
import net.minecraft.core.BlockPos
import net.minecraft.nbt.{ CompoundTag, Tag }
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.{ BlockEntity, BlockEntityType }
import net.minecraft.world.level.block.state.BlockState

final class CosmosBlockEntity[
                                +S <: Tickable with Savable[Tag]
                              ](
                                  typeIn : BlockEntityType[_ <: CosmosBlockEntity[S]],
                                  posIn : BlockPos,
                                  stateIn : BlockState,
                                  fabric : BlockEntityBehaviourFabric[S]
                                ) extends BlockEntity(typeIn, posIn, stateIn):
  private var tag : Option[CompoundTag] = None
  lazy val state : S = fabric.createBehaviour(LevelRef, tag.map(v => FunctionRef(() => v)), BlockPosRef, FunctionRef(() => getBlockState))
  
  override def load(tag : CompoundTag): Unit =
    super.load(tag)
    assert(tag.isEmpty)
    this.tag = Some(tag)
  end load
  
  override def saveAdditional(tag : CompoundTag): Unit =
    super.saveAdditional(tag)
    tag.put("state", state.save)
  end saveAdditional
  
  def tickServer() : Unit =
    state.tick()
  end tickServer
  
  private object BlockPosRef extends BlockPosition:
    override def x: Int = getBlockPos.getX
    override def y: Int = getBlockPos.getY
    override def z: Int = getBlockPos.getZ
  end BlockPosRef
  
  private object LevelRef extends Ref[Level | Null]:
    override def value: Level | Null = getLevel
  end LevelRef
end CosmosBlockEntity
