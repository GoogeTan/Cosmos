package me.katze.cosmos.common.data

final class MemoizeRef[T](initialRef : Ref[T]) extends Ref[T]:
  override lazy val value: T = initialRef.value
end MemoizeRef

