package me.katze.cosmos.block.entity.behaviour.fluid

import me.katze.cosmos.common.data.Ref
import net.minecraft.world.level.material.{ Fluid, Fluids }

final class FancyFluidStackMergeResultFluid(first: Ref[Fluid], second: Ref[Fluid]) extends Ref[Fluid]:
  override def value: Fluid =
    if first.value == Fluids.EMPTY then
      second.value
    else if second.value == Fluids.EMPTY then
      first.value
    else
      assert(first.value.isSame(second.value))
      first.value
    end if
  end value
end FancyFluidStackMergeResultFluid
