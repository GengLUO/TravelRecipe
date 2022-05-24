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
    private List<Country> countries;

    public User(int userID) {
        this.userID = userID;
        countries = new ArrayList<Country>();
    }

    public List<Country> getCountriesByContinentID(int continentID)
    {
        Country country;
        List<Country> newCountries = new  ArrayList<Country>();
        for (int i = 0; i < countries.size(); i++) {
            country = countries.get(i);
            if (country.getContinent() == continentID)
            {
                newCountries.add(country);
            }
        }
        return newCountries;
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
    }

    public void setCountryAmericaAmount(int countryAmericaAmount) {
        this.countryAmericaAmount = countryAmericaAmount;
    }

    public void setCountryEuropeAmount(int countryEuropeAmount) {
        this.countryEuropeAmount = countryEuropeAmount;
    }

    public void setCountryAfricaAmount(int countryAfricaAmount) {
        this.countryAfricaAmount = countryAfricaAmount;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;

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

    public List<Country> getCountries() {
        return countries;
    }


}
