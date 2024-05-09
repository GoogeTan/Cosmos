package me.katze.cosmos.entity

import me.katze.cosmos.common.based.Postmen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player

final class CommonPlayer(player : Player) extends Postmen:
  override def sendMessage(text: String): Unit =
    player.displayClientMessage(Component.literal(text), false)
  end sendMessage
end CommonPlayer

