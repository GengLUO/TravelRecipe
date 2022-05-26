package be.kuleuven.travelrecipe.adapters;

import java.util.List;

import be.kuleuven.travelrecipe.models.Recipe;

public interface RecipeNotifier {
    void notifyRecipesListChanged(List<Recipe> recipes);
}
