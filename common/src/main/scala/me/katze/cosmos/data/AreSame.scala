package me.katze.cosmos.data

final class AreSame[T : Equiv](first : Ref[T], second : Ref[T]) extends Ref[Boolean]:
  override def value: Boolean = summon[Equiv[T]].equiv(first.value, second.value)
end AreSame

