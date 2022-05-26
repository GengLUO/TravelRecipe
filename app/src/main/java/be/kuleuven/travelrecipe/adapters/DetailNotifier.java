package be.kuleuven.travelrecipe.adapters;

import java.util.LinkedHashMap;
import java.util.List;

import be.kuleuven.travelrecipe.models.RecipeStep;

public interface DetailNotifier {
    void notifyDetailsRetrieved(List<RecipeStep> steps);
    void notifyIngredientsRetrieved(LinkedHashMap<String,String> ingredients);
    void notifyLikeStateChanged(boolean newState);
}
