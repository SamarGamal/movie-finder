package org.themoviedb.movie_finder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themoviedb.movie_finder.model.Genre;
import org.themoviedb.movie_finder.repository.GenreRepository;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    /**
     * Retrieve all genres
     * @return List<Genre> -> list of genres
     */
    public List<Genre> getAllGenres(){
        return genreRepository.findAll();
    }

    /**
     * Create or update a genre if exists
     * @param genre
     * @return Genre
     */
    public Genre saveGenre(Genre genre){
        return genreRepository.save(genre);
    }

    /**
     * Save list of genres in the table
     * @param genres
     * @return List<Genre> -> list of genres
     */
    @Transactional
    public List<Genre> saveAllGenres(List<Genre> genres){
        return genreRepository.saveAll(genres);
    }

    /**
     * Retrieve genre by ID
     * @param id
     * @return Genre
     */
    public Genre getGenreById(Long id){
        return genreRepository.findById(id).orElse(null);
    }

    /**
     * Get Genre using TmdbId
     * @param TmdbId
     * @return Genre
     */
    public Genre getGenreByTmdbId(Long TmdbId){
        return genreRepository.findByTmdbId(TmdbId);
    }

    public List<Genre> getGenresByTmdbIds(List<Long> tmdbIds){
        return genreRepository.findByTmdbIdIn(tmdbIds);
    }

    public void deleteGenre(){
        // TODO: to be implemented
    }
}
