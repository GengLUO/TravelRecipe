package be.kuleuven.travelrecipe.notifier;

import java.util.List;

import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;

public interface RecipeNotifier {
    void notifyRecipesListChanged(List<RecipeInfo> recipes);
}
