package katze.cosmos.init

import net.minecraftforge.eventbus.api.IEventBus

trait Registrable:
  def registerAt(bus : IEventBus) : Unit
end Registrable
