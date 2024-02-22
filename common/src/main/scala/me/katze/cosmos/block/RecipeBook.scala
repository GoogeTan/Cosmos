package me.katze.cosmos.block

trait RecipeBook[-Ingredient, +Output]:
  def recipeFor(ingredient : Ingredient) : Output
end RecipeBook

