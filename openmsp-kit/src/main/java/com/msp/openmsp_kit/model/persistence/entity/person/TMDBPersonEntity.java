package com.msp.openmsp_kit.model.persistence.entity.person;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tmdb_people")
public class TMDBPersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adult")
    boolean adult;
    @Column(name = "also_known_as")
    List<String> alsoKnownAs;
    @Column(name = "biography", length = 5000)
    String biography;
    @Column(name = "birthday")
    String birthday;
    @Column(name = "deathday")
    String deathday;
    @Column(name = "gender")
    int gender;
    @Column(name = "homepage")
    String homepage;
    @Column(name = "tmdb_id")
    int tmdbId;
    @Column(name = "imdb_id")
    String imdbId;
    @Column(name = "known_for_department", length = 1000)
    String knownForDepartment;
    @Column(name = "name")
    String name;
    @Column(name = "place_of_birth")
    String placeOfBirth;
    @Column(name = "popularity")
    double popularity;
    @Column(name = "profile_path")
    String profilePath;
    @Column(name = "iso_639_1")
    String iso6391;
}
