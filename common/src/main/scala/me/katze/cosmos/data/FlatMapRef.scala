package me.katze.cosmos.data

final class FlatMapRef[A, B](initial : Ref[A], f : A => Ref[B]) extends Ref[B]:
  override def value: B = f(initial.value).value
end FlatMapRef
