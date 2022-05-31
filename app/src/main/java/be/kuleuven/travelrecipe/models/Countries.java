package be.kuleuven.travelrecipe.models;

import java.util.ArrayList;
import java.util.List;

public class Countries extends AbstractCountries{


    private int userid;
    private List<Country> countries;



    public Countries(int userid) {
        this.userid = userid;
        this.countries = new ArrayList<Country>();
    }
    public List<Country> getCountries() {
        return countries;
    }
    public void setCountries(List<Country> countries) {
        this.countries = countries;
        countryActivityNotifier.setCountriesRecyclerView(countries);
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
}
