package me.katze.cosmos.common.based.data

/**
 * Обёртка, позволяющая данным вести себя как объекты
 *
 * Необходимо для предания данным ленивости, вычислимости или иных свойств.
 * @tparam T тип значения
 */
trait Ref[+T]:
  def value : T
  
  def flatMap[B](f : T => Ref[B]) : Ref[B] = FlatMapRef(this, f)
  
  def map[B](f : T => B) : Ref[B] = MapRef(this, f)
end Ref

extension[T](value : T)
  def ref : Ref[T] = FunctionRef[T](() => value)
end extension
  