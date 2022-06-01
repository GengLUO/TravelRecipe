package be.kuleuven.travelrecipe.models.recipe;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class DetailedRecipe extends AbstractDetailedRecipe {
    private RecipeInfo recipeInfo;
    private boolean likeState;
    private List<RecipeStep> steps = new ArrayList<>();

    public DetailedRecipe() {
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
        detailNotifier.notifyDetailsRetrieved(steps);
    }

    public RecipeInfo getRecipeInfo() {
        return recipeInfo;
    }

    public void setRecipeInfo(RecipeInfo recipeInfo) {
        this.recipeInfo = recipeInfo;
    }

    public void setRecipeDemo(Bitmap bitmap){
        getRecipeInfo().setDemo(bitmap);
        detailNotifier.notifyRecipeDemoRetrieved(bitmap);
    }

    public boolean isLikeState() {
        return likeState;
    }

    public void setLikeState(boolean likeState) {
        this.likeState = likeState;
        detailNotifier.notifyLikeStateChanged(likeState);
    }
}
