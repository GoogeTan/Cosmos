package me.katze.cosmos.common.block

trait Recipe:
  def empty : Boolean
  def complete : Boolean
end Recipe
