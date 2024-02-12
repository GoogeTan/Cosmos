package katze.cosmos.block.entity

import katze.cosmos.block.Position
import katze.cosmos.block.entity.behaviour.{ BlockEntityBehaviour, BlockEntityBehaviourFabric }
import katze.cosmos.data.{ FunctionRef, Ref }
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.{ BlockEntity, BlockEntityType }
import net.minecraft.world.level.block.state.BlockState

class CosmosBlockEntity[+S <: BlockEntityBehaviour](
                                                    typeIn : BlockEntityType[_ <: CosmosBlockEntity[S]],
                                                    posIn : BlockPos,
                                                    stateIn : BlockState,
                                                    fabric : BlockEntityBehaviourFabric[S]
                                                  ) extends BlockEntity(typeIn, posIn, stateIn):
  private var tag : Option[CompoundTag] = None
  lazy val state : S = fabric.createBehaviour(LevelRef, FunctionRef(() => tag), BlockPosRef, FunctionRef(() => getBlockState))
  
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
    state.tickServer()
  end tickServer
  
  private object BlockPosRef extends Position:
    override def x: Int = getBlockPos.getX
    override def y: Int = getBlockPos.getY
    override def z: Int = getBlockPos.getZ
  end BlockPosRef
  
  private object LevelRef extends Ref[Level | Null]:
    override def value: Level | Null = getLevel
  end LevelRef
end CosmosBlockEntity
