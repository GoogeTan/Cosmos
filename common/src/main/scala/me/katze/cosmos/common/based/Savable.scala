package me.katze.cosmos.common.based

trait Savable[+T]:
  def save: T
end Savable
