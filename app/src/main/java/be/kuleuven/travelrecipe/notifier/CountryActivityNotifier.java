package be.kuleuven.travelrecipe.notifier;

import java.util.List;

import be.kuleuven.travelrecipe.R;
import be.kuleuven.travelrecipe.models.Country;

public interface CountryActivityNotifier {
    void setCountriesRecyclerView(List<Country> countries);


}