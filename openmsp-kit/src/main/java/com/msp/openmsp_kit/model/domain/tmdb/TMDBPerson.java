package com.msp.openmsp_kit.model.domain.tmdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TMDBPerson {
    boolean adult;
    List<String> alsoKnownAs;
    String biography;
    String birthday;
    String deathday;
    int gender;
    String homepage;
    int tmdbId;
    String imdbId;
    String knownForDepartment;
    String name;
    String placeOfBirth;
    double popularity;
    String profilePath;
    String iso_639_1;
}
