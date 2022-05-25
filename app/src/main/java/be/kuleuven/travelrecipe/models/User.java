package be.kuleuven.travelrecipe.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class User extends AbstractUser{

    private int userID;
    private String userName;
    private int level;
    private int recipeAmount;
    private int countryAsiaAmount;
    private int countryAmericaAmount;
    private int countryEuropeAmount;
    private int countryAfricaAmount;
    private Bitmap image;


    public User(int userID) {
        this.userID = userID;

    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


    public int getRecipeAmount() {
        return recipeAmount;
    }

    public void setRecipeAmount(int recipeAmount) {
        this.recipeAmount = recipeAmount;
        homepageFragmentNotifier.notifyRecipeNumberChanged();
    }
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        homepageFragmentNotifier.notifyImageChanged();
    }

    public void setUserName(String userName) {
        this.userName = userName;
        homepageFragmentNotifier.notifyNameChanged();
    }

    public void setLevel(int level) {
        this.level = level;
        homepageFragmentNotifier.notifyLevelChanged();
    }

    public void setCountryAsiaAmount(int countryAsiaAmount) {
        this.countryAsiaAmount = countryAsiaAmount;
        homepageFragmentNotifier.notifyAsiaChanged();
    }

    public void setCountryAmericaAmount(int countryAmericaAmount) {
        this.countryAmericaAmount = countryAmericaAmount;
        homepageFragmentNotifier.notifyAmericaChanged();
    }

    public void setCountryEuropeAmount(int countryEuropeAmount) {
        this.countryEuropeAmount = countryEuropeAmount;
        homepageFragmentNotifier.notifyEuropeChanged();
    }

    public void setCountryAfricaAmount(int countryAfricaAmount) {
        this.countryAfricaAmount = countryAfricaAmount;
        homepageFragmentNotifier.notifyAfricaChanged();
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public int getLevel() {
        return level;
    }

    public int getCountryAsiaAmount() {
        return countryAsiaAmount;
    }

    public int getCountryAmericaAmount() {
        return countryAmericaAmount;
    }

    public int getCountryEuropeAmount() {
        return countryEuropeAmount;
    }

    public int getCountryAfricaAmount() {
        return countryAfricaAmount;
    }



}
