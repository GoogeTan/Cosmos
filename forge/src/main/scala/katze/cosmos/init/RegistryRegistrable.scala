package katze.cosmos.init

import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.{ ResourceKey, ResourceLocation }
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.RegisterEvent

import java.util.function.Supplier

final class RegistryRegistrable[T](
                                    key : ResourceKey[_ <: Registry[T]],
                                    location : ResourceLocation,
                                    value : Supplier[T],
                                  ) extends Registrable:
  override def registerAt(bus: IEventBus): Unit =
    bus.addListener[RegisterEvent]:
      event => event.register(key, location, value)
  end registerAt
end RegistryRegistrable

object RegistryRegistrable:
  def Block(location : ResourceLocation, value : Supplier[Block]) : RegistryRegistrable[Block] =
    RegistryRegistrable(
      Registries.BLOCK,
      location,
      value
    )
  end Block
end RegistryRegistrable