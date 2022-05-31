package be.kuleuven.travelrecipe.models.dashboard;

import be.kuleuven.travelrecipe.notifier.RecipeNotifier;

public abstract class AbstractDashboard {
    protected RecipeNotifier recipeNotifier;
    public final void setRecipeNotifier(RecipeNotifier recipeNotifier){
        this.recipeNotifier = recipeNotifier;
    }
}
