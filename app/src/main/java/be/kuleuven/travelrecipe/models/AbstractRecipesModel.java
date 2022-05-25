package be.kuleuven.travelrecipe.models;

import be.kuleuven.travelrecipe.adapters.RecipeNotifier;

public abstract class AbstractRecipesModel {
    protected RecipeNotifier recipeNotifier;
    public final void setRecipeNotifier(RecipeNotifier recipeNotifier){
        this.recipeNotifier = recipeNotifier;
    }
}
