package me.katze.cosmos.data

final class Lazy[T](initial : Ref[T]) extends Ref[T]:
  override lazy val value: T = initial.value
end Lazy
