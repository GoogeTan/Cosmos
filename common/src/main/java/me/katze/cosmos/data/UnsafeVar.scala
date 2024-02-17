package me.katze.cosmos.data

final class UnsafeVar[T](uninitializedException : => Exception) extends Memory[T]:
  private var _value : Option[T] = None
  override def value: T = _value.getOrElse(throw uninitializedException)
  override def value_=(newValue : T): Unit = _value = Some(newValue)
end UnsafeVar