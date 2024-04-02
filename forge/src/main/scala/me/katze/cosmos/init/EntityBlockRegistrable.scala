package me.katze.cosmos.init

import me.katze.cosmos.CosmosException
import me.katze.cosmos.block.CosmosEntityBlock
import me.katze.cosmos.block.entity.CosmosBlockEntity
import me.katze.cosmos.block.entity.behaviour.BlockEntityBehaviourFabric
import me.katze.cosmos.common.{ Savable, Usable }
import me.katze.cosmos.common.block.Tickable
import me.katze.cosmos.common.data.UnsafeVar
import me.katze.cosmos.entity.CommonPlayer
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.{ CompoundTag, Tag }
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.RegisterEvent

final class EntityBlockRegistrable[
                                    +B <: Tickable with Savable[Tag] with Usable[CommonPlayer]
                                  ](
                                      name : ResourceLocation,
                                      properties : BlockBehaviour.Properties,
                                      defaultBlockEntityState : BlockEntityBehaviourFabric[B]
                                    ) extends Registrable:
  override def registerAt(bus: IEventBus): Unit =
    val typeIn = UnsafeVar[BlockEntityType[_ <: CosmosBlockEntity[B]]](CosmosException("BlockEntityType hasn't been initialised before access. It seems that it hasn't been registered."))
    // lazy надо чтобы в конструкторе блок не дёрнул лишнего
    lazy val block: CosmosEntityBlock[B] = CosmosEntityBlock(properties, typeIn, defaultBlockEntityState)
    // Тут lazy надо чтобы не дёрнуть блок раньше времени
    lazy val entityType =
      val res = BlockEntityType.Builder.of((pos, state) => block.newBlockEntity(pos, state), block).build(null)
      typeIn.value = res
      res
    end entityType
    
    bus.addListener[RegisterEvent]:
        event => event.register(Registries.BLOCK, name, () => block)
    bus.addListener[RegisterEvent]:
        event => event.register(Registries.BLOCK_ENTITY_TYPE, name, () => entityType)
  end registerAt
end EntityBlockRegistrable
