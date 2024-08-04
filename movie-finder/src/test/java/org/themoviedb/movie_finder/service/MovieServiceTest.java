package org.themoviedb.movie_finder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.themoviedb.movie_finder.model.Movie;
import org.themoviedb.movie_finder.repository.MovieRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;

    @BeforeEach
    public void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setTmdbId(100L);
    }

    @Test
    public void testGetAllMovies() {
        List<Movie> movies = Arrays.asList(movie);
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();
        assertEquals(movies, result);
    }

    @Test
    public void testSaveMovie() {
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie result = movieService.saveMovie(movie);
        assertEquals(movie, result);
    }

    @Test
    public void testSaveAllMovies() {
        List<Movie> movies = Arrays.asList(movie);
        when(movieRepository.saveAll(movies)).thenReturn(movies);

        movieService.saveAll(movies);
        verify(movieRepository, times(1)).saveAll(movies);
    }

    @Test
    public void testGetMovieById() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovieById(1L);
        assertEquals(movie, result);
    }

    @Test
    public void testGetMovieById_NotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        Movie result = movieService.getMovieById(1L);
        assertNull(result);
    }

    @Test
    public void testGetMovieByTmdbId() {
        when(movieRepository.findByTmdbId(100L)).thenReturn(movie);

        Movie result = movieService.getMovieByTmdbId(100L);
        assertEquals(movie, result);
    }
}