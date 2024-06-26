package me.katze.cosmos.common.based.data

final class Lazy[T](initial : Ref[T]) extends Ref[T]:
  override lazy val value: T = initial.value
end Lazy
