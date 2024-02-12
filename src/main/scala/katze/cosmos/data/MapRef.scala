package katze.cosmos.data

final class MapRef[A, B](initial : Ref[A], func : A => B) extends Ref[B]:
  override def value: B = func(initial.value)
end MapRef
