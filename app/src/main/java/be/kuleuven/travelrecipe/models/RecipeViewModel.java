package be.kuleuven.travelrecipe.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.toolbox.Volley;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    private MutableLiveData<List<Recipe>> recipes;

    private void loadRecipes() {
        //Volley.newRequestQueue()
    }

    public LiveData<List<Recipe>> getRecipes(){
        if(recipes == null){
            recipes = new MutableLiveData<List<Recipe>>();
            loadRecipes();
        }
        return recipes;
    }

    public void setRecipes(List<Recipe> newRecipes){
        recipes.setValue(newRecipes);
    }

}
