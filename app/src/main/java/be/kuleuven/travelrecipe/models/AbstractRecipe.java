package be.kuleuven.travelrecipe.models;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRecipe {
    private String name;
    private String country;
    private int demo;
    private List<RecipeStep> steps = new ArrayList<>();

    public abstract List<RecipeStep> getSteps();
}
