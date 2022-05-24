package be.kuleuven.travelrecipe.models;

import be.kuleuven.travelrecipe.adapters.CountryActivityNotifier;
import be.kuleuven.travelrecipe.adapters.HomepageFragmentNotifier;

public abstract class AbstractUser {
    //user controls HomepageFragment and CountryActivity
    protected HomepageFragmentNotifier homepageFragmentNotifier;
    protected CountryActivityNotifier countryActivityNotifier;
    public final void setHomepageFragmentNotifier(HomepageFragmentNotifier homepageFragmentNotifier)
    {
        this.homepageFragmentNotifier = homepageFragmentNotifier;
    }
    public final void setCountryActivityNotifier(CountryActivityNotifier countryActivityNotifier)
    {
        this.countryActivityNotifier = countryActivityNotifier;
    }
}
