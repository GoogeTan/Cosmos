package me.katze.cosmos.common.data

/**
 * Обёртка, позволяющая данным вести себя как объекты
 *
 * Необходимо для предания данным ленивости, вычислимости или иных свойств.
 * @tparam T тип значения
 */
trait Ref[+T]:
  def value : T
end Ref
