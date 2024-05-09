package me.katze.cosmos.common.block

import me.katze.cosmos.common.based.Savable
import me.katze.cosmos.common.based.fluid.{ Countable, CountableSource }
import me.katze.cosmos.common.block.fluid.Sink

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

