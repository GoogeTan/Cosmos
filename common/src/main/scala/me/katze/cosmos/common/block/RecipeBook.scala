package me.katze.cosmos.common.block

trait RecipeBook[-Ingredient, +Output]:
  def recipeFor(ingredient : Ingredient) : Output
end RecipeBook

