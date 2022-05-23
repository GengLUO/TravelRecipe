package be.kuleuven.travelrecipe.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String name;
    private String country;
    private Bitmap demo;
    private List<RecipeStep> steps = new ArrayList<>();

    public Recipe(String title, String content, Bitmap demo) {
        this.name = title;
        this.country = content;
        this.demo = demo;
    }


    public List<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
    }

    public void addRecipeSteps(RecipeStep recipeStep){
        steps.add(recipeStep);
    }
}
