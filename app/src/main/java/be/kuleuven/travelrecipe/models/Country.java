package be.kuleuven.travelrecipe.models;

import be.kuleuven.travelrecipe.R;

public class Country {
    private int countryImg;
    private String countryName;
    private int recipeNumber;
    private int continent;
    private boolean actived;

    public int getContinent() {
        return continent;
    }

    public void setContinent(int continent) {
        this.continent = continent;
    }

    public int getCountryImg() {
        return countryImg;
    }

    public void setCountryImg(int countryImg) {
        this.countryImg = countryImg;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getRecipeNumber() {
        return recipeNumber;
    }

    public void setRecipeNumber(int recipeNumber) {
        this.recipeNumber = recipeNumber;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public Country(int countryImg, String countryName, int recipeNumber, boolean actived, int continent) {
        this.countryImg = countryImg;
        this.countryName = countryName;
        this.recipeNumber = recipeNumber;
        this.actived = actived;
        this.continent = continent;
    }
}
