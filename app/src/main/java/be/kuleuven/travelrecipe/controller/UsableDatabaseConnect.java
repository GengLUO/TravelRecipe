package be.kuleuven.travelrecipe.controller;

import be.kuleuven.travelrecipe.models.User;

public interface UsableDatabaseConnect {
    User retrieveUserInfo(User inuser);
    User retrieveCountries(User inuser);
}
