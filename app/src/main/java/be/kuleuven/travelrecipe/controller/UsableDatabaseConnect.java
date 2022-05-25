package be.kuleuven.travelrecipe.controller;

import android.graphics.Bitmap;
import android.view.View;

import be.kuleuven.travelrecipe.models.Recipe;
import be.kuleuven.travelrecipe.models.User;

public interface UsableDatabaseConnect {
    User retrieveUserInfo(User inuser);
    User retrieveCountries(User inuser);
    void postProfileImage(View caller, Bitmap bitmap, User user);
    void uploadRecipe(View caller, Recipe recipe, int userid);
    void uploadStep(View caller, int recipeid, int sequence, String description,Bitmap image);
}
