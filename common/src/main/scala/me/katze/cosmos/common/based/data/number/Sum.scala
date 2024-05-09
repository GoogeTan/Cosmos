package me.katze.cosmos.common.based.data.number

import cats.Semigroup
import cats.syntax.all.{ *, given }
import me.katze.cosmos.common.based.data.Ref

final class Sum[T : Semigroup](first : Ref[T], second : Ref[T]) extends Ref[T]:
  override def value: T = first.value |+| second.value
end Sum
