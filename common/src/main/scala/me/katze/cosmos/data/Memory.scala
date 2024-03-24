package me.katze.cosmos.data

/**
 * Обобщение хранилища в памяти. Может не иметь реального стабильного значения(ссылаться на Tag при любых записях и чтениях)
 * @tparam T тип значения
 */
trait Memory[T] extends Ref[T]:
  def value_=(newValue : T): Unit
end Memory
