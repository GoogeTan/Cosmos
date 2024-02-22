package me.katze.cosmos.block

import me.katze.cosmos.Savable
import me.katze.cosmos.block.fluid.{ Countable, CountableSource, Sink }

final class SingleStackProcessorBehaviour[
                                            Ingredient,
                                            Stack <: Countable,
                                            ProcessingProcess <: Tickable & Recipe & Savable[Storage],
                                            Storage
                                          ](
                                            input : CountableSource[Stack, Ingredient],
                                            private var currentStack : ProcessingProcess,
                                            book : RecipeBook[Ingredient, (Int, Stack => ProcessingProcess)],
                                            val outputs : List[Sink[ProcessingProcess]]
                                          ) extends Tickable, Savable[Storage]:
  override def tick(): Unit =
    if currentStack.empty then
      val (amountRequired, recipeFabric) = book.recipeFor(input.ingredient)
      input.askForExact(amountRequired).foreach:
        stack =>
          currentStack = recipeFabric(stack)
    else
      currentStack.tick()
      if currentStack.complete then
        outputs.foreach(_.tryTakeFrom(currentStack))
      end if
    end if
  end tick
  
  override def save: Storage =
    currentStack.save
  end save
end SingleStackProcessorBehaviour

