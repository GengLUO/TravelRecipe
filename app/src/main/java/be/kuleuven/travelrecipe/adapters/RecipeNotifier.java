package be.kuleuven.travelrecipe.adapters;

import java.util.List;

import be.kuleuven.travelrecipe.models.RecipeInfo;

public interface RecipeNotifier {
    void notifyRecipesListChanged(List<RecipeInfo> recipes);
}
