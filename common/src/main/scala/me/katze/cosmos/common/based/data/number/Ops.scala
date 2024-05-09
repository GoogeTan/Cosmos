package me.katze.cosmos.common.based.data.number

import me.katze.cosmos.common.based.data.{ Ref, ref }

import scala.annotation.targetName

extension[T : Numeric](ref : Ref[T])
  @targetName("times")
  def *(another : Ref[T]) : Ref[T] =
    for
      a <- ref
      b <- ref
    yield Numeric[T].times(a, b)
  end *
  
  @targetName("plus")
  def +(another: Ref[T]): Ref[T] =
    for
      a <- ref
      b <- ref
    yield Numeric[T].plus(a, b)
  end +
  
  @targetName("minus")
  def -(another: Ref[T]): Ref[T] =
    for
      a <- ref
      b <- ref
    yield Numeric[T].minus(a, b)
  end -
  
  @targetName("times")
  def *(another : T) : Ref[T] = ref * another.ref
  @targetName("plus")
  def +(another : T) : Ref[T] = ref + another.ref
  @targetName("minus")
  def -(another : T) : Ref[T] = ref - another.ref
end extension