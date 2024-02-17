package me.katze.cosmos.data

final class Conditional[T](condition : Ref[Boolean], trueCase : Ref[T], falseCase : Ref[T]) extends Ref[T]:
  override def value: T =
    if condition.value then
      trueCase.value
    else
      falseCase.value
    end if
  end value
end Conditional

