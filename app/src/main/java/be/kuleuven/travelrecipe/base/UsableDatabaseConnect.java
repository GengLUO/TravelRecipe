package be.kuleuven.travelrecipe.base;

import android.graphics.Bitmap;
import android.view.View;

import be.kuleuven.travelrecipe.models.country.Countries;
import be.kuleuven.travelrecipe.models.dashboard.Dashboard;
import be.kuleuven.travelrecipe.models.recipe.DetailedRecipe;
import be.kuleuven.travelrecipe.models.recipe.RecipeInfo;
import be.kuleuven.travelrecipe.models.user.User;
import be.kuleuven.travelrecipe.views.activities.LoginActivity;

public interface UsableDatabaseConnect {
    void retrieveUserInfo(User inuser);
    void retrieveCountries(Countries countries);
    void retrieveCountryByContinent(Countries countries, int continent);
    void retrieveContinentInfo( User user);
    void setUserInfo(View caller,int userid, String username,String password);
    void login(LoginActivity loginActivity, String username, String password);
    void register(View caller, String username, String password);
    void postProfileImage(View caller, Bitmap bitmap, User user);
    void uploadRecipe(View caller, RecipeInfo recipe, int userid);
    void uploadStep(View caller, int recipeid, int sequence, String description,Bitmap image);
    void retrieveRecipes(Dashboard dashboard);
    void requestRecipeDetails(DetailedRecipe detailedRecipeDetails);
    void uploadMealPlan(int userId, DetailedRecipe detailedRecipe,boolean state);
    void requestLikeState(int userId, DetailedRecipe detailedRecipe);
    void requestRecipeDemo(DetailedRecipe detailedRecipe);
    void uploadWork(int recipeId, int userId, Bitmap bitmap);
    void requestListRecipe(Dashboard dashboard, int userId);
}
