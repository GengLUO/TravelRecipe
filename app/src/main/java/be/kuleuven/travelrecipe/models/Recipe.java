package be.kuleuven.travelrecipe.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String name, description;
    private int country;
    private Bitmap demo;
    private List<RecipeStep> steps = new ArrayList<>();

    public Recipe(String title, String desc , Bitmap demo) {
        this.name = title;
        this.description = desc;
        this.demo = demo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public Bitmap getDemo() {
        return demo;
    }

    public void setDemo(Bitmap demo) {
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
