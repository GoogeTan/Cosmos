package me.katze.cosmos.common.block

enum InteractionResult extends Enumeration:
  case SUCCESS, CONSUME, CONSUME_PARTIAL, PASS, FAIL
  
  def consumesAction: Boolean =
    (this eq SUCCESS) || (this eq CONSUME) || (this eq CONSUME_PARTIAL)
  end consumesAction
  
  def shouldSwing: Boolean =
    this eq SUCCESS
  end shouldSwing
  
  def shouldAwardStats: Boolean =
    (this eq SUCCESS) || (this eq CONSUME)
  end shouldAwardStats
  
  def sidedSuccess(side: Boolean): InteractionResult =
    if side then SUCCESS else CONSUME
  end sidedSuccess
end InteractionResult
