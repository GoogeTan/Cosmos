package katze.cosmos.init
import net.minecraftforge.eventbus.api.IEventBus

final class IterableRegistrable(values : IterableOnce[Registrable]) extends Registrable:
  override def registerAt(bus: IEventBus): Unit =
    values.iterator.foreach(_.registerAt(bus))
  end registerAt
end IterableRegistrable