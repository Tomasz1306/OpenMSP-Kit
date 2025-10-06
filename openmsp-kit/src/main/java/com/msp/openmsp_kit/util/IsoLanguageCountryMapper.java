package com.msp.openmsp_kit.util;

import java.util.HashMap;
import java.util.Map;

public class IsoLanguageCountryMapper {

    private static final Map<String, String> LANG_TO_COUNTRY = new HashMap<>();
    private static final Map<String, String> COUNTRY_TO_LANG = new HashMap<>();

    static {
        LANG_TO_COUNTRY.put("en", "US");
        LANG_TO_COUNTRY.put("es", "ES");
        LANG_TO_COUNTRY.put("fr", "FR");
        LANG_TO_COUNTRY.put("de", "DE");
        LANG_TO_COUNTRY.put("it", "IT");
        LANG_TO_COUNTRY.put("pl", "PL");
        LANG_TO_COUNTRY.put("pt", "PT");
        LANG_TO_COUNTRY.put("ru", "RU");
        LANG_TO_COUNTRY.put("ja", "JP");
        LANG_TO_COUNTRY.put("ko", "KR");
        LANG_TO_COUNTRY.put("zh", "CN");
        LANG_TO_COUNTRY.put("ar", "SA");
        LANG_TO_COUNTRY.put("nl", "NL");
        LANG_TO_COUNTRY.put("sv", "SE");
        LANG_TO_COUNTRY.put("no", "NO");
        LANG_TO_COUNTRY.put("da", "DK");
        LANG_TO_COUNTRY.put("fi", "FI");
        LANG_TO_COUNTRY.put("cs", "CZ");
        LANG_TO_COUNTRY.put("tr", "TR");
        LANG_TO_COUNTRY.put("el", "GR");
        LANG_TO_COUNTRY.put("hu", "HU");

        for (Map.Entry<String, String> e : LANG_TO_COUNTRY.entrySet()) {
            COUNTRY_TO_LANG.put(e.getValue(), e.getKey());
        }
    }

    public static String toCountry(String iso639_1) {
        return LANG_TO_COUNTRY.getOrDefault(iso639_1.toLowerCase(), iso639_1.toUpperCase());
    }

    public static String getCountryFromLanguage(String iso639_1) {
        return LANG_TO_COUNTRY.get(iso639_1);
    }

    public static String toLanguage(String iso3166_1) {
        return COUNTRY_TO_LANG.getOrDefault(iso3166_1.toUpperCase(), iso3166_1.toLowerCase());
    }
}