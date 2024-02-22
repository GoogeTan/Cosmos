package me.katze.cosmos.block

trait Recipe:
  def empty : Boolean
  def complete : Boolean
end Recipe
