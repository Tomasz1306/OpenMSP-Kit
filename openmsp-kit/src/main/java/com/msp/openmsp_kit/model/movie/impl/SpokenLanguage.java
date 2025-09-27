package com.msp.openmsp_kit.model.movie.impl;

public class SpokenLanguage {
    private String englishName;
    private String iso_639_1;
    private String name;

    public SpokenLanguage(String englishName, String iso_639_1, String name) {
        this.englishName = englishName;
        this.iso_639_1 = iso_639_1;
        this.name = name;
    }

    public SpokenLanguage() {}

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "englishName='" + englishName + "/'" + iso_639_1 + "/'" + name + "/";
    }
}
