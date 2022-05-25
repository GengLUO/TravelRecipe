package be.kuleuven.travelrecipe.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RecipesModel extends AbstractRecipesModel{
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

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
        recipeNotifier.setDashboardRecyclerView(recipes);
    }


    public List<Recipe> getAllRecipes() {
        return recipes;
    }
}
