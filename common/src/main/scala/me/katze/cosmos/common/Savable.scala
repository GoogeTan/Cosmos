package me.katze.cosmos.common

trait Savable[+T]:
  def save: T
end Savable
