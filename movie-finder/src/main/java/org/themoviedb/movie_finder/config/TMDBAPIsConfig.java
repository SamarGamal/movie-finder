package org.themoviedb.movie_finder.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for TMDB APIs
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "tmdb")
public class TMDBAPIsConfig {
    private String accessToken;
    private String host;
    private String popularMoviesAPI;
    private String genresAPI;
    private int numPagesPopularMovies;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(host)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .build();
    }
}
