package katze.cosmos.init

import katze.cosmos.block.CosmosEntityBlock
import katze.cosmos.block.entity.CosmosBlockEntity
import katze.cosmos.block.entity.behaviour.BlockEntityBehaviourFabric
import me.katze.cosmos.Savable
import me.katze.cosmos.block.BlockEntityBehaviour
import me.katze.cosmos.data.UnsafeVar
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.RegisterEvent

final class EntityBlockRegistrable[
                                    +B <: BlockEntityBehaviour with Savable[CompoundTag]
                                  ](
                                      name : ResourceLocation,
                                      properties : BlockBehaviour.Properties,
                                      defaultBlockEntityState : BlockEntityBehaviourFabric[B]
                                    ) extends Registrable:
  override def registerAt(bus: IEventBus): Unit =
    val typeIn = UnsafeVar[BlockEntityType[_ <: CosmosBlockEntity[B]]](Exception("BlockEntityType hasn't been initialised before access. It seems that it hasn't been registered."))
    // lazy надо чтобы в конструкторе блок не дёрнул лишнего
    lazy val block: CosmosEntityBlock[B] = CosmosEntityBlock(properties, typeIn, defaultBlockEntityState)
    // Тут lazy надо чтобы не дёрнуть блок раньше времени
    lazy val entityType =
      val res = BlockEntityType.Builder.of((pos, state) => block.newBlockEntity(pos, state), block).build(null)
      typeIn.value = res
      res
    bus.addListener[RegisterEvent]:
        event => event.register(Registries.BLOCK, name, () => block)
    bus.addListener[RegisterEvent]:
        event => event.register(Registries.BLOCK_ENTITY_TYPE, name, () => entityType)
  end registerAt
end EntityBlockRegistrable