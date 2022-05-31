package be.kuleuven.travelrecipe.models.country;

import be.kuleuven.travelrecipe.notifier.CountryActivityNotifier;

public abstract class AbstractCountries {
    protected CountryActivityNotifier countryActivityNotifier;
    public final void setCountryActivityNotifier(CountryActivityNotifier countryActivityNotifier)
    {
        this.countryActivityNotifier = countryActivityNotifier;
    }
}
