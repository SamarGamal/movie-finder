package org.themoviedb.movie_finder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.themoviedb.movie_finder.model.Movie;

/**
 * Repository for movies table
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTmdbId(Long tmdbId);
}
