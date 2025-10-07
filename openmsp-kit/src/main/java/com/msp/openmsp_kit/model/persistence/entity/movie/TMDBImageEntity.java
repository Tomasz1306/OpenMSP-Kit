package com.msp.openmsp_kit.model.persistence.entity.movie;

import com.msp.openmsp_kit.model.persistence.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tmdb_images")
public class TMDBImageEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "aspect_ratio")
    private double aspectRatio;
    @Column(name = "height")
    private int height;
    @Column(name = "iso_639_1")
    private String iso_639_1;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "vote_average")
    private double voteAverage;
    @Column(name ="vote_count")
    private int voteCount;
    @Column(name = "width")
    private int width;
    @Column(name = "type")
    private String type;


    @ManyToOne
    @JoinColumn(name = "tmdb_id", nullable = false)
    private TMDBMovieEntity movie;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
