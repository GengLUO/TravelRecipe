package be.kuleuven.travelrecipe.base;

import java.util.HashMap;
import java.util.Locale;

public class CountryImageTranslation {

    private HashMap<String,String> map = new HashMap<>();

    public CountryImageTranslation() {
        String[] isoCountries = Locale.getISOCountries();
        for (String country : isoCountries) {
            Locale locale = new Locale("en", country);
            String name = locale.getDisplayCountry();
            String code = locale.getCountry();
            map.put(name,code);
        }
    }

    public String generateFlag(int countryId){
        String abbreviation = Locale.getISOCountries()[countryId-1];
        int firstLetter = Character.codePointAt(abbreviation, 0) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt(abbreviation, 1) - 0x41 + 0x1F1E6;
        return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
    }

    public String generateFlag(String fullName){
        try {
            System.out.println(fullName);
            String code = map.get(fullName);
            System.out.println(code);
            int firstLetter = Character.codePointAt(code, 0) - 0x41 + 0x1F1E6;
            int secondLetter = Character.codePointAt(code, 1) - 0x41 + 0x1F1E6;
            return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
