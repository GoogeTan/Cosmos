package me.katze.cosmos

trait Savable[+T]:
  def save : T
end Savable
