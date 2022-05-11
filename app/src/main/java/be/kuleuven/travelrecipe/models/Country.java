package be.kuleuven.travelrecipe.models;

public class Country {
    private String countryname;
    private int number;
    private int image;

    public Country(String countryname, int number, int image) {
        this.countryname = countryname;
        this.number = number;
        this.image = image;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
