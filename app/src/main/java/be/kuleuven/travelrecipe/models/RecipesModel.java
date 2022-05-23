package be.kuleuven.travelrecipe.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RecipesModel implements IRecipesModel {
    protected List<Recipe> recipes = new LinkedList<>();

    public RecipesModel() {
    }


    public void addRecipe(Recipe recipe){
        recipes.add(recipe);
    }

    public Recipe getRecipe(int index){
        return recipes.get(index);
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

    public void loadRecipes(){

    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipes;
    }
}
