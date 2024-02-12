package katze.cosmos.block

import com.mojang.serialization.MapCodec
import katze.cosmos.block.entity.CosmosBlockEntity
import katze.cosmos.block.entity.behaviour.{ BlockEntityBehaviour, BlockEntityBehaviourFabric }
import katze.cosmos.data.Ref
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.BaseEntityBlock.createTickerHelper
import net.minecraft.world.level.block.entity.{ BlockEntity, BlockEntityTicker, BlockEntityType }
import net.minecraft.world.level.block.state.BlockBehaviour.simpleCodec
import net.minecraft.world.level.block.state.{ BlockBehaviour, BlockState }

final class CosmosEntityBlock[S <: BlockEntityBehaviour](
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
end CosmosEntityBlock
