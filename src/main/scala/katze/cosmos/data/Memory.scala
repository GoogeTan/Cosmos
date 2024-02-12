package katze.cosmos.data

trait Memory[T] extends Ref[T]:
  def value_=(newValue : T): Unit
end Memory
