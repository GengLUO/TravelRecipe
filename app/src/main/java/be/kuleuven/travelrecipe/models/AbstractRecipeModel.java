package be.kuleuven.travelrecipe.models;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class AbstractRecipeModel {
    private static LinkedList<Recipe> recipes = new LinkedList<>();
    public abstract void addRecipe(Recipe recipe);

    public abstract Recipe getRecipe(int index);

    public abstract void deleteRecipe(Recipe recipe);

    public abstract void addRecipes(ArrayList<Recipe> newRecipes);

    public abstract void clearRecipes();

    public abstract void loadRecipes();
}
