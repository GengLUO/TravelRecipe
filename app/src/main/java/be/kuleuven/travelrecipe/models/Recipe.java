package be.kuleuven.travelrecipe.models;

import java.util.LinkedList;
import java.util.List;

public class Recipe {

    private String title;
    private String content;
    private String stepNr, stepDesc;
    private int stepImg;
    private List<RecipeStep> recipeSteps = new LinkedList<>();

    public Recipe(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Recipe(String stepNr, String stepDesc, int stepImg) {
        this.stepNr = stepNr;
        this.stepDesc = stepDesc;
        this.stepImg = stepImg;
    }

    public Recipe() {
    }

    public String getStepNr() {
        return stepNr;
    }

    public void setStepNr(String stepNr) {
        this.stepNr = stepNr;
    }

    public String getStepDesc() {
        return stepDesc;
    }

    public void setStepDesc(String stepDesc) {
        this.stepDesc = stepDesc;
    }

    public int getStepImg() {
        return stepImg;
    }

    public void setStepImg(int stepImg) {
        this.stepImg = stepImg;
    }

    public List<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(List<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }



    public void addRecipeSteps(RecipeStep recipeStep){
        recipeSteps.add(recipeStep);
    }
}
