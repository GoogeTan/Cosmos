package me.katze.cosmos.common.based.data

final class FunctionRef[T](val getter : () => T) extends Ref[T]:
  override def value: T = getter()
end FunctionRef
