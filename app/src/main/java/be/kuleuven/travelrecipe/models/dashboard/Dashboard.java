package be.kuleuven.travelrecipe.models.dashboard;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;


public class Dashboard extends AbstractDashboard {
    private List<RecipeInfo> recipes = new ArrayList<>();

    public Dashboard() {
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
