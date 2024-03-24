package me.katze.cosmos.block

import me.katze.cosmos.Savable
import me.katze.cosmos.block.fluid.{ Countable, CountableSource, Sink }

final class SingleStackProcessorBehaviour[
                                            Ingredient,
                                            Stack <: Countable,
                                            RecipeInProgress <: Tickable & Recipe & Savable[Storage],
                                            +Storage
                                          ](
                                              private val ingredientSource : CountableSource[Stack, Ingredient],
                                              private var currentRecipe    : RecipeInProgress,
                                              private val book             : RecipeBook[Ingredient, (Int, Stack => RecipeInProgress)],
                                              private val output           : Sink[RecipeInProgress]
                                          ) extends Tickable, Savable[Storage]:
  override def tick(): Unit =
    if currentRecipe.empty then
      tryStartCooking()
    else
      currentRecipe.tick()
      if currentRecipe.complete then
        output.tryTakeFrom(currentRecipe)
      end if
    end if
  end tick
  
  private def tryStartCooking() : Unit =
    val (amountRequired, recipeFabric) = book.recipeFor(ingredientSource.ingredient)
    ingredientSource.askForExact(amountRequired).foreach:
      stack =>
        currentRecipe = recipeFabric(stack)
  end tryStartCooking
  
  override def save: Storage =
    currentRecipe.save
  end save
end SingleStackProcessorBehaviour

