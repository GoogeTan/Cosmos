package katze.cosmos.data

final class UnsafeVar[T](private var _value : Option[T]) extends Memory[T]:
  override def value: T = _value.get
  override def value_=(newValue : T): Unit = _value = Some(newValue)
end UnsafeVar