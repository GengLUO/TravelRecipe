package be.kuleuven.travelrecipe.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RecipesDashboard extends AbstractRecipesDashboard {
    private List<RecipeInfo> recipes = new LinkedList<>();

    public RecipesDashboard() {
    }


    public void addRecipe(RecipeInfo recipe){
        recipes.add(recipe);
    }

    public RecipeInfo getRecipeInfo(int index){
        return recipes.get(index);
    }

    public void deleteRecipe(RecipeInfo recipe){
        recipes.remove(recipe);
    }

    public void addRecipes(ArrayList<RecipeInfo> newRecipes){
        recipes.addAll(newRecipes);
    }

    public void clearRecipes(){
        recipes.clear();
    }

    public void loadRecipes(){

    }

    public void setRecipes(List<RecipeInfo> recipes){
        this.recipes = recipes;
        recipeNotifier.notifyRecipesListChanged(recipes);
    }

    public List<RecipeInfo> getAllRecipes() {
        return recipes;
    }
}
