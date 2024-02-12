package katze.cosmos.data

final class FunctionRef[T](getter : () => T) extends Ref[T]:
  override def value: T = getter()
end FunctionRef

