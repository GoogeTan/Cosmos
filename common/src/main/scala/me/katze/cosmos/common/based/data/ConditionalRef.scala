package me.katze.cosmos.common.based.data

final class ConditionalRef[T](inCase : Ref[Boolean], use : Ref[T], otherwise : Ref[T]) extends Ref[T]:
  override def value: T =
    if inCase.value then
      use.value
    else
      otherwise.value
    end if
  end value
end ConditionalRef

