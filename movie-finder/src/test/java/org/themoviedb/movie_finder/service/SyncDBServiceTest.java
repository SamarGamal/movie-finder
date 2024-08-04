package org.themoviedb.movie_finder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.themoviedb.movie_finder.config.TMDBAPIsConfig;
import org.themoviedb.movie_finder.model.Genre;
import org.themoviedb.movie_finder.model.Movie;
import org.themoviedb.movie_finder.service.TMDBService.TMDBMovie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SyncDBServiceTest {
    @InjectMocks
    private SyncDBService syncDBService;
    @Mock
    private TMDBService tmdbService;
    @Mock
    private GenreService genreService;
    @Mock
    private MovieService movieService;
    @Mock
    private TMDBAPIsConfig tmdbapIsConfig;
    private Genre genre;
    private TMDBMovie tmdbMovie;
    private Movie movie;

    @BeforeEach
    public void setUp() {
        genre = new Genre();
        genre.setId(1L);
        genre.setName("Action");
        genre.setTmdbId(1L);

        tmdbMovie = new TMDBMovie(1L, "Inception", "2010-07-16", 82.0, 8.8, Arrays.asList(1L));

        movie = new Movie();
        movie.setId(1L);
        movie.setTmdbId(1L);
        movie.setTitle("Inception");
        movie.setReleaseDate("2010-07-16");
        movie.setPopularity(82.0);
        movie.setVoteAverage(8.8);
        movie.setGenres(Arrays.asList(genre));
    }

    @Test
    public void testSyncDatabase() {
        // Mock the behavior of tmdbService
        when(tmdbService.getAllGenres()).thenReturn(Arrays.asList(genre));
        when(genreService.getGenreByTmdbId(1L)).thenReturn(null);
        when(tmdbapIsConfig.getNumPagesPopularMovies()).thenReturn(1);
        when(tmdbService.getPopularMovies(1)).thenReturn(Arrays.asList(tmdbMovie));
        when(genreService.getGenresByTmdbIds(Arrays.asList(1L))).thenReturn(Arrays.asList(genre));

        syncDBService.syncDatabase();

        verify(tmdbService, times(1)).getAllGenres();
        verify(genreService, times(1)).getGenreByTmdbId(1L);
        verify(genreService, times(1)).saveAllGenres(any(List.class));
        verify(tmdbapIsConfig, times(1)).getNumPagesPopularMovies();
        verify(tmdbService, times(1)).getPopularMovies(1);
        verify(movieService, times(1)).saveAll(any(List.class));
    }

    @Test
    public void testSyncDatabase_NoMovies() {
        // Mock the behavior of tmdbService and other dependencies
        when(tmdbService.getAllGenres()).thenReturn(Arrays.asList(genre));
        when(genreService.getGenreByTmdbId(1L)).thenReturn(null);
        when(tmdbapIsConfig.getNumPagesPopularMovies()).thenReturn(1);
        when(tmdbService.getPopularMovies(1)).thenReturn(null);

        syncDBService.syncDatabase();

        // Verify the interactions with the dependencies
        verify(tmdbService, times(1)).getAllGenres();
        verify(genreService, times(1)).getGenreByTmdbId(1L);
        verify(genreService, times(1)).saveAllGenres(any(List.class));
        verify(tmdbapIsConfig, times(1)).getNumPagesPopularMovies();
        verify(tmdbService, times(1)).getPopularMovies(1);
        verify(movieService, never()).saveAll(any(List.class));
    }

    @Test
    public void testSyncDatabase_NoGenres() {
        // Mock the behavior of tmdbService and other dependencies
        when(tmdbService.getAllGenres()).thenReturn(new ArrayList<>());
        when(tmdbapIsConfig.getNumPagesPopularMovies()).thenReturn(1);
        when(tmdbService.getPopularMovies(1)).thenReturn(null);

        syncDBService.syncDatabase();

        // Verify the interactions with the dependencies
        verify(tmdbService, times(1)).getAllGenres();
        verify(genreService, never()).getGenreByTmdbId(1L);
        verify(tmdbapIsConfig, times(1)).getNumPagesPopularMovies();
        verify(tmdbService, times(1)).getPopularMovies(1);
        verify(movieService, never()).saveAll(any(List.class));
    }
}
