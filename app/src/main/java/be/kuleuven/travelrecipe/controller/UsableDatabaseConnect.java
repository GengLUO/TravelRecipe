package be.kuleuven.travelrecipe.controller;

import android.graphics.Bitmap;
import android.view.View;

import java.util.List;

import be.kuleuven.travelrecipe.models.country.Countries;
import be.kuleuven.travelrecipe.models.dashboard.Dashboard;
import be.kuleuven.travelrecipe.models.recipe.DetailedRecipe;
import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;
import be.kuleuven.travelrecipe.models.user.User;
import be.kuleuven.travelrecipe.views.activities.LoginActivity;

public interface UsableDatabaseConnect {
    User retrieveUserInfo(User inuser);
    User retrieveCountries(User inuser);
    Countries retrieveCountryByContinent(Countries countries, int continent);
    List<RecipeInfo> retrieveRecipes();
    void retrieveContinentInfo( User user);
    void setUserInfo(View caller,int userid, String username,String password);
    void login(LoginActivity loginActivity, String username, String password);
    void register(View caller, String username, String password);
    void postProfileImage(View caller, Bitmap bitmap, User user);
    void uploadRecipe(View caller, RecipeInfo recipe, int userid);
    void uploadStep(View caller, int recipeid, int sequence, String description,Bitmap image);
    void retrieveRecipes(Dashboard dashboard);
    void requestIngredients(DetailedRecipe detailedRecipeDetails);
    void requestRecipeDetails(DetailedRecipe detailedRecipeDetails);
}
