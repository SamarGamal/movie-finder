package org.themoviedb.movie_finder.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.themoviedb.movie_finder.config.TMDBAPIsConfig;
import org.themoviedb.movie_finder.constants.enums.Languages;
import org.themoviedb.movie_finder.model.Genre;

import java.util.List;

/**
 * TMDB service which consumes TMDB apis
 * and populate the data in the database
 */
@Service
public class TMDBService {
    @Autowired
    private TMDBAPIsConfig tmdbapIsConfig;
    @Autowired
    private WebClient webClient;

    /**
     * fetch popular movies per page
     * @param page the page will be retrieved
     * @return List<Movie> list of movies
     */
    public List<TMDBMovie> getPopularMovies(int page){
        PopularMoviesTMDBResponse popularMoviesTMDBResponse = webClient.get().uri( uriBuilder -> uriBuilder.path(tmdbapIsConfig.getPopularMoviesAPI())
                        .queryParam("language", Languages.EN)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .bodyToMono(PopularMoviesTMDBResponse.class)
                .block();
        return popularMoviesTMDBResponse != null ? popularMoviesTMDBResponse.results : null;
    }

    /**
     * fetch all genres
     * @return List<Genre> list of genres
     */
    public List<Genre> getAllGenres(){
        GenresTMDBResponse genresTMDBResponse = webClient.get().uri( uriBuilder -> uriBuilder.path(tmdbapIsConfig.getGenresAPI())
                        .queryParam("language", Languages.EN.toString())
                        .build())
                .retrieve()
                .bodyToMono(GenresTMDBResponse.class)
                .block();
        return genresTMDBResponse != null ? genresTMDBResponse.genres : null;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class GenresTMDBResponse{
        private List<Genre> genres;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class PopularMoviesTMDBResponse{
        private int page;
        private List<TMDBMovie> results;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TMDBMovie{
        private Long id;
        private String title;
        private String release_date;
        private double popularity;
        private double vote_average;
        private List<Long> genre_ids;
    }
}
