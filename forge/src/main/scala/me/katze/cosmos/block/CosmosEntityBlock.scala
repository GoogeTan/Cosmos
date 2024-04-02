package me.katze.cosmos.block

import com.mojang.serialization.MapCodec
import me.katze.cosmos.block.entity.CosmosBlockEntity
import me.katze.cosmos.block.entity.behaviour.BlockEntityBehaviourFabric
import me.katze.cosmos.common
import me.katze.cosmos.common.block.{ InteractionResult, Tickable }
import me.katze.cosmos.common.data.Ref
import me.katze.cosmos.common.position.{ BlockPosition, EntityPosition }
import me.katze.cosmos.common.{ Hand, Savable, Usable, block, BlockHitResult as CommonBlockHitResult }
import me.katze.cosmos.entity.CommonPlayer
import net.minecraft.core.{ BlockPos, Direction }
import net.minecraft.nbt.Tag
import net.minecraft.world
import net.minecraft.world.*
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.BaseEntityBlock.createTickerHelper
import net.minecraft.world.level.block.entity.{ BlockEntity, BlockEntityTicker, BlockEntityType }
import net.minecraft.world.level.block.state.BlockBehaviour.simpleCodec
import net.minecraft.world.level.block.state.{ BlockBehaviour, BlockState }
import net.minecraft.world.phys.{ BlockHitResult, HitResult }

final class CosmosEntityBlock[
                                S <: Tickable with Savable[Tag] with Usable[CommonPlayer]
                              ](
                                  properties : BlockBehaviour.Properties,
                                  entityTypeRef : Ref[BlockEntityType[_ <: CosmosBlockEntity[S]]],
                                  defaultBlockEntityState : BlockEntityBehaviourFabric[S]
                                ) extends BaseEntityBlock(properties):
  def newBlockEntity(pos: BlockPos, state: BlockState): CosmosBlockEntity[S] =
    CosmosBlockEntity[S](entityTypeRef.value, pos, state, defaultBlockEntityState)
  end newBlockEntity
  
  override protected def codec: MapCodec[CosmosEntityBlock[S]] = simpleCodec(props => CosmosEntityBlock(props, entityTypeRef, defaultBlockEntityState))
  
  override def getTicker[T <: BlockEntity](level : Level, state : BlockState, typeIn2 : BlockEntityType[T]): BlockEntityTicker[T] =
    createTickerHelper(typeIn2, entityTypeRef.value, (_: Level, _: BlockPos, _: BlockState, t: CosmosBlockEntity[S]) => t.tickServer())
  end getTicker
  
  override def use(state : BlockState, level : Level, pos : BlockPos, player : Player, hand : InteractionHand, hitResult : BlockHitResult): world.InteractionResult =
    level.getBlockEntity(pos) match
      case entity: CosmosBlockEntity[?] =>
        asVanilaResult(entity.use(CommonPlayer(player), asCommonHand(hand), asCommonHitResult(hitResult)))
      case _ => ???
    end match
  end use
  
  // TODO refactor this hell
  def asVanilaResult(interactionResult: block.InteractionResult): world.InteractionResult =
    interactionResult match
      case block.InteractionResult.SUCCESS => world.InteractionResult.SUCCESS
      case block.InteractionResult.CONSUME => world.InteractionResult.CONSUME
      case block.InteractionResult.CONSUME_PARTIAL => world.InteractionResult.CONSUME_PARTIAL
      case block.InteractionResult.PASS => world.InteractionResult.PASS
      case block.InteractionResult.FAIL => world.InteractionResult.FAIL
    end match
  end asVanilaResult
  
  def asCommonHand(hand : InteractionHand) : Hand =
    hand match
      case InteractionHand.MAIN_HAND => Hand.Main
      case InteractionHand.OFF_HAND => Hand.Off
    end match
  end asCommonHand
  
  def asCommonHitResult(hit : BlockHitResult) : CommonBlockHitResult =
    CommonBlockHitResult(
      hit.getType == HitResult.Type.MISS,
      Vec3Location(hit.getLocation.x.toFloat, hit.getLocation.y.toFloat, hit.getLocation.z.toFloat),
      asCommonDirection(hit.getDirection),
      VanilaBlockPosition(hit.getBlockPos.getX, hit.getBlockPos.getY, hit.getBlockPos.getZ),
      hit.isInside
    )
  end asCommonHitResult
  
  def asCommonDirection(direction : Direction): common.Direction =
    direction match
      case Direction.DOWN  => common.Direction.Down
      case Direction.UP    => common.Direction.Up
      case Direction.NORTH => common.Direction.North
      case Direction.SOUTH => common.Direction.South
      case Direction.WEST  => common.Direction.West
      case Direction.EAST  => common.Direction.East
    end match
  end asCommonDirection
  
  final class VanilaBlockPosition(override val x : Int, override val y : Int, override val z : Int) extends BlockPosition
  final class Vec3Location(override val x : Float, override val y : Float, override val z : Float) extends EntityPosition
end CosmosEntityBlock
