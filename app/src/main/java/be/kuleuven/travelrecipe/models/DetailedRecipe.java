package be.kuleuven.travelrecipe.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DetailedRecipe extends AbstractDetailedRecipe {
    private RecipeInfo recipe;
    private LinkedHashMap<String, String> ingredients;
    private List<RecipeStep> steps = new ArrayList<>();

    public DetailedRecipe() {
    }

    public LinkedHashMap<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(LinkedHashMap<String, String> ingredients) {
        this.ingredients = ingredients;
        detailNotifier.notifyIngredientsRetrieved(ingredients);
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
        detailNotifier.notifyDetailsRetrieved(steps);
    }

    public RecipeInfo getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeInfo recipe) {
        this.recipe = recipe;
    }
}
