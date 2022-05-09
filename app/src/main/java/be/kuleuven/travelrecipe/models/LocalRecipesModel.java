package be.kuleuven.travelrecipe.models;

import java.util.ArrayList;
import java.util.LinkedList;

public class LocalRecipesModel {

    private static LinkedList<Recipe> recipes = new LinkedList<>();

    public void addRecipe(Recipe recipe){
        recipes.add(recipe);
    }

    public void deleteRecipe(Recipe recipe){
        recipes.remove(recipe);
    }

    public void addRecipes(ArrayList<Recipe> newRecipes){
        recipes.addAll(newRecipes);
    }

    public void clearRecipes(){
        recipes.clear();
    }
}
