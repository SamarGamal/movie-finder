package org.themoviedb.movie_finder.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.themoviedb.movie_finder.config.SyncDBConfig;
import org.themoviedb.movie_finder.config.TMDBAPIsConfig;
import org.themoviedb.movie_finder.model.Genre;
import org.themoviedb.movie_finder.model.Movie;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SyncDBService {
    @Autowired
    private TMDBService tmdbService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private TMDBAPIsConfig tmdbapIsConfig;
    @Autowired
    private SyncDBConfig syncDBConfig;
    private static final Logger logger = LogManager.getLogger(SyncDBService.class);

    /**
     * scheduler to sync tmdb database daily
     */
    @Scheduled(fixedRateString = "#{@syncDBConfig.getFixedRate()}")
    public void syncDatabase() {
        // sync genres in the db
        logger.info("Sync Genres in DB at " + LocalDateTime.now());
        syncGenresDB();

        // Sync movies in the db
        logger.info("Sync Movies in DB at " + LocalDateTime.now());
        syncPopularMoviesDB();
    }

    /**
     * Get Genres from TMDB and sync them in DB
     */
    private void syncGenresDB(){
        List<Genre> genres = tmdbService.getAllGenres();
        for(Genre genre: genres){
            Genre genreFound = genreService.getGenreByTmdbId(genre.getId());
            if(genreFound != null) {
                genre.setId(genreFound.getId());
                genre.setTmdbId(genreFound.getTmdbId());
            }
            else genre.setTmdbId(genre.getId());
        }
        genreService.saveAllGenres(genres);
    }

    /**
     * Get popular movies from TMDB and sync them in DB
     */
    private void syncPopularMoviesDB(){
        int page = 1;
        int numberOfpages = tmdbapIsConfig.getNumPagesPopularMovies();
        List<Movie> moviesList = new ArrayList<>();
        while(page <= numberOfpages){
            List<TMDBService.TMDBMovie> movies = tmdbService.getPopularMovies(page);
            if(movies == null) break;
            moviesList.clear();
            for(TMDBService.TMDBMovie tmdbMovie: movies){
                Movie movie = new Movie();
                Movie movieFound = movieService.getMovieByTmdbId(tmdbMovie.getId());
                if(movieFound != null) movie.setId(movieFound.getId());

                movie.setTmdbId(tmdbMovie.getId());
                movie.setTitle(tmdbMovie.getTitle());
                movie.setPopularity(tmdbMovie.getPopularity());
                movie.setReleaseDate(tmdbMovie.getRelease_date());
                movie.setVoteAverage(tmdbMovie.getVote_average());

                List<Genre> movieGenres = genreService.getGenresByTmdbIds(tmdbMovie.getGenre_ids());
                movie.setGenres(movieGenres);
                moviesList.add(movie);
            }
            movieService.saveAll(moviesList);
            page++;
        }
    }
}
