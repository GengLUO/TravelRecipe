package be.kuleuven.travelrecipe.models.user;

import be.kuleuven.travelrecipe.notifier.HomepageFragmentNotifier;

public abstract class AbstractUser {
    //user controls HomepageFragment and CountryActivity
    protected HomepageFragmentNotifier homepageFragmentNotifier;
    public final void setHomepageFragmentNotifier(HomepageFragmentNotifier homepageFragmentNotifier)
    {
        this.homepageFragmentNotifier = homepageFragmentNotifier;
    }

}
