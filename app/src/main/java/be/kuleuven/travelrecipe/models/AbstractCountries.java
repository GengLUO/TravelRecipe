package be.kuleuven.travelrecipe.models;

import be.kuleuven.travelrecipe.adapters.CountryActivityNotifier;

public abstract class AbstractCountries {
    protected CountryActivityNotifier countryActivityNotifier;
    public final void setCountryActivityNotifier(CountryActivityNotifier countryActivityNotifier)
    {
        this.countryActivityNotifier = countryActivityNotifier;
    }
}
