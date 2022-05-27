package be.kuleuven.travelrecipe.models;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CountryImageTranslation {


    public String generateFlag(int countryId){
        String abbreviation = Locale.getISOCountries()[countryId-1];
        int firstLetter = Character.codePointAt(abbreviation, 0) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt(abbreviation, 1) - 0x41 + 0x1F1E6;
        return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
    }

}
