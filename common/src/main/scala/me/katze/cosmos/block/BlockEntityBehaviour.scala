package me.katze.cosmos.block

import me.katze.cosmos.Savable

trait BlockEntityBehaviour:
  def tickServer(): Unit
  
  def tickClient() : Unit
end BlockEntityBehaviour