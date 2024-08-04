package org.themoviedb.movie_finder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.themoviedb.movie_finder.model.Movie;
import org.themoviedb.movie_finder.repository.MovieRepository;

import java.util.List;

/**
 * Movie service
 */
@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    /**
     * Retrieve all movies
     * @return List<Movie> -> list of movies
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Create or update a movie if exists
     * @param movie
     * @return Movie -> created or updated movie
     */
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    /**
     * save all movies
     * @param movies
     */
    public void saveAll(List<Movie> movies){
        movieRepository.saveAll(movies);
    }

    /**
     * Retrieve movie by id
     * @param id -> id of the movie
     * @return Movie
     */
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    /**
     * Retrieve a movie by its tmdbId
     * @param tmdbId
     * @return Movie
     */
    public Movie getMovieByTmdbId(Long tmdbId){ return movieRepository.findByTmdbId(tmdbId); }

    public void deleteMovie(){
        // TODO: to be implemented
    }
}
