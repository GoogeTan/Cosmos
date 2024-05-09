package me.katze.cosmos.common.based.data

final class SidedRef[T](side : Side, server : Ref[T], client : Ref[T]) extends Ref[T]:
  override def value: T =
    if side.isServer then
      server.value
    else
      client.value
    end if 
  end value
end SidedRef
