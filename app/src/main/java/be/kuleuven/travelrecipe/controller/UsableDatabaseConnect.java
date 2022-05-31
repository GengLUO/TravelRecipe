package be.kuleuven.travelrecipe.controller;

import android.graphics.Bitmap;
import android.view.View;

import java.util.List;

import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;
import be.kuleuven.travelrecipe.models.User;

public interface UsableDatabaseConnect {
    User retrieveUserInfo(User inuser);
    User retrieveCountries(User inuser);
    List<RecipeInfo> retrieveRecipes();
    void postProfileImage(View caller, Bitmap bitmap, User user);
    void uploadRecipe(View caller, RecipeInfo recipe, int userid);
    void uploadStep(View caller, int recipeid, int sequence, String description,Bitmap image);
}
