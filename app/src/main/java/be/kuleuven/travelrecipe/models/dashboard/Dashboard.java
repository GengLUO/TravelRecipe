package be.kuleuven.travelrecipe.models.dashboard;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;
import be.kuleuven.travelrecipe.models.recipe.RecipeIngredient;


public class Dashboard extends AbstractDashboard {
    private List<RecipeInfo> dashboard;
    private static final int DATE_ASC = 0;
    private static final int DATE_DESC = 1;
    private static final int A_Z = 2;
    private static final int Z_A = 3;

    public Dashboard() {
         dashboard = new ArrayList<>();
    }

    public void addRecipe(RecipeInfo recipe){
        dashboard.add(recipe);
    }

    public RecipeInfo getRecipeInfo(int index){
        return dashboard.get(index);
    }

    public void deleteRecipe(RecipeInfo recipe){
        dashboard.remove(recipe);
    }

    public void addRecipes(ArrayList<RecipeInfo> newRecipes){
        dashboard.addAll(newRecipes);
    }

    public void clearRecipes(){
        dashboard.clear();
    }

    public void setDashboard(List<RecipeInfo> dashboard){
        this.dashboard = dashboard;
        recipeNotifier.notifyRecipesListChanged(dashboard);
    }

    public List<RecipeInfo> getAllRecipes() {
        return dashboard;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<RecipeInfo> filterList(String text) {
        String lowercaseText = text.toLowerCase();
        return this.getAllRecipes()
                .stream()
                .filter(r -> r.getName().toLowerCase().contains(lowercaseText) ||
                        r.getCountryname().toLowerCase().contains(lowercaseText) ||
                        r.getIngredients()
                                .stream()
                                .map(RecipeIngredient::getName)
                                .collect(Collectors.toSet())
                                .toString()
                                .toLowerCase()
                                .contains(lowercaseText))
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortList(int state){
        switch (state){
            case DATE_DESC : this.getAllRecipes().sort(Comparator.comparingInt(RecipeInfo::getRecipeId).reversed());
                break;
            case A_Z:       this.getAllRecipes().sort((Comparator.comparing(RecipeInfo::getName)));
                break;
            case Z_A:       this.getAllRecipes().sort((Comparator.comparing(RecipeInfo::getName).reversed()));
                break;
            case DATE_ASC :
            default:        this.getAllRecipes().sort(Comparator.comparingInt(RecipeInfo::getRecipeId));
        }
    }
}
