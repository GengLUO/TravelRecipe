package be.kuleuven.travelrecipe.models;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RecipeIngredients extends AbstractRecipeDetails{
    private HashMap<String,String> ingredients;

    public RecipeIngredients(HashMap<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setIngredients(LinkedHashMap<String, String> ingredients) {
        this.ingredients = ingredients;
        detailNotifier.notifyIngredientsRetrieved(ingredients);
    }
}
