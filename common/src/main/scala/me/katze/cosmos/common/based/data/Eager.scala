package me.katze.cosmos.common.based.data

final class Eager[T](another : Ref[T]) extends Ref[T]:
  override val value: T = another.value
end Eager
