package be.kuleuven.travelrecipe.notifier;

import android.graphics.Bitmap;

import java.util.List;

import be.kuleuven.travelrecipe.models.recipe.RecipeStep;

public interface DetailNotifier {
    void notifyDetailsRetrieved(List<RecipeStep> steps);
    //void notifyIngredientsRetrieved(LinkedHashMap<String,String> ingredients);
    void notifyLikeStateChanged(boolean newState);
    void notifyRecipeDemoRetrieved(Bitmap bitmap);
}
