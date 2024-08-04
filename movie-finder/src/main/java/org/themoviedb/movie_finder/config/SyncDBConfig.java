package org.themoviedb.movie_finder.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for sync data
 * in app database from TMDB APIs
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "syncdb")
public class SyncDBConfig {
    private Long fixedRate;
}
