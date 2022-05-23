package be.kuleuven.travelrecipe.models;

import java.util.ArrayList;
import java.util.List;

public interface IRecipesModel {
    void addRecipe(Recipe recipe);
    Recipe getRecipe(int index);
    void deleteRecipe(Recipe recipe);
    void addRecipes(ArrayList<Recipe> newRecipes);
    void clearRecipes();
    void loadRecipes();
    List<Recipe> getAllRecipes();
}
