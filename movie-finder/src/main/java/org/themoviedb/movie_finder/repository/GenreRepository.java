package org.themoviedb.movie_finder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.themoviedb.movie_finder.model.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Repository for genres table
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByTmdbId(Long TmbdbId);
    List<Genre> findByTmdbIdIn(List<Long> ids);
}
