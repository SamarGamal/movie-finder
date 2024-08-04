package org.themoviedb.movie_finder.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entity class for genres table
 */
@Getter
@Setter
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false, unique = true)
    private Long tmdbId;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;
}
