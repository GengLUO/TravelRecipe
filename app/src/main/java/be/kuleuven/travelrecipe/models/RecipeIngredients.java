package be.kuleuven.travelrecipe.models;

import java.util.HashMap;
import java.util.Map;

public class RecipeIngredients {
    private HashMap<String,String> ingredients;

    public RecipeIngredients(HashMap<String, String> ingredients) {
        this.ingredients = ingredients;
    }
}
