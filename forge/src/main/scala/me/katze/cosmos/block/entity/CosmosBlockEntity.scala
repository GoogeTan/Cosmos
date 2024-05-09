package me.katze.cosmos.block.entity

import me.katze.cosmos.block.entity.behaviour.BlockEntityBehaviourFabric
import me.katze.cosmos.common.based.data.{ FunctionRef, Ref }
import me.katze.cosmos.common.based.position.BlockPosition
import me.katze.cosmos.common.based.{ BlockHitResult, Hand, Savable, Usable }
import me.katze.cosmos.common.block.{ InteractionResult, Tickable }
import me.katze.cosmos.entity.CommonPlayer
import net.minecraft.core.BlockPos
import net.minecraft.nbt.{ CompoundTag, Tag }
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.{ BlockEntity, BlockEntityType }
import net.minecraft.world.level.block.state.BlockState

final class CosmosBlockEntity[
                                S <: Tickable with Savable[Tag] with Usable[CommonPlayer]
                              ](
                                  typeIn : BlockEntityType[_ <: CosmosBlockEntity[S]],
                                  posIn : BlockPos,
                                  stateIn : BlockState,
                                  fabric : BlockEntityBehaviourFabric[S]
                                ) extends BlockEntity(typeIn, posIn, stateIn) with Usable[CommonPlayer]:
  private var state : Option[S] = None
  
  override def load(tag : CompoundTag): Unit =
    super.load(tag)
    assert(state.isEmpty)
    val tagRef = FunctionRef(() => tag.getCompound("state"))
    state = Some(fabric.createBehaviour(LevelRef, Some(tagRef), BlockPosRef, BlockStateRef))
  end load
  
  override def saveAdditional(tag : CompoundTag): Unit =
    super.saveAdditional(tag)
    state match
      case Some(value) => tag.put("state", value.save)
      case None => ()
    end match
  end saveAdditional
  
  def tickServer() : Unit =
    if state.isEmpty then
      state = Some(fabric.createBehaviour(LevelRef, None, BlockPosRef, BlockStateRef))
    end if
    state.get.tick()
  end tickServer
  
  override def use(player: CommonPlayer, hand: Hand, result: BlockHitResult): InteractionResult =
    state.get.use(player, hand, result)
  end use
  
  private object BlockPosRef extends BlockPosition:
    override def x: Int = getBlockPos.getX
    override def y: Int = getBlockPos.getY
    override def z: Int = getBlockPos.getZ
  end BlockPosRef
  private def LevelRef : Ref[Level | Null] = FunctionRef[Level | Null](getLevel)
  private def BlockStateRef : Ref[BlockState] = FunctionRef(getBlockState)
end CosmosBlockEntity
