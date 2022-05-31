package be.kuleuven.travelrecipe.notifier;

import java.util.List;

import be.kuleuven.travelrecipe.models.country.Country;

public interface CountryActivityNotifier {
    void setCountriesRecyclerView(List<Country> countries);


}
