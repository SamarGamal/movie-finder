package org.themoviedb.movie_finder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.themoviedb.movie_finder.model.Genre;
import org.themoviedb.movie_finder.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    @InjectMocks
    private GenreService genreService;
    @Mock
    private GenreRepository genreRepository;
    private Genre genre;

    @BeforeEach
    public void setUp() {
        genre = new Genre();
        genre.setId(1L);
        genre.setName("Action");
        genre.setTmdbId(100L);
    }

    @Test
    public void testGetAllGenres() {
        List<Genre> genres = Arrays.asList(genre);
        when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> result = genreService.getAllGenres();
        assertEquals(genres, result);
    }

    @Test
    public void testSaveGenre() {
        when(genreRepository.save(genre)).thenReturn(genre);

        Genre result = genreService.saveGenre(genre);
        assertEquals(genre, result);
    }

    @Test
    public void testSaveAllGenres() {
        List<Genre> genres = Arrays.asList(genre);
        when(genreRepository.saveAll(genres)).thenReturn(genres);

        List<Genre> result = genreService.saveAllGenres(genres);
        assertEquals(genres, result);
    }

    @Test
    public void testGetGenreById() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        Genre result = genreService.getGenreById(1L);
        assertEquals(genre, result);
    }

    @Test
    public void testGetGenreById_NotFound() {
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());

        Genre result = genreService.getGenreById(1L);
        assertNull(result);
    }

    @Test
    public void testGetGenreByTmdbId() {
        when(genreRepository.findByTmdbId(100L)).thenReturn(genre);

        Genre result = genreService.getGenreByTmdbId(100L);
        assertEquals(genre, result);
    }

    @Test
    public void testGetGenresByTmdbIds() {
        List<Long> tmdbIds = Arrays.asList(100L);
        List<Genre> genres = Arrays.asList(genre);
        when(genreRepository.findByTmdbIdIn(tmdbIds)).thenReturn(genres);

        List<Genre> result = genreService.getGenresByTmdbIds(tmdbIds);
        assertEquals(genres, result);
    }
}