package org.themoviedb.movie_finder.constants.enums;

/**
 * Enum for language parameters for TMDB APIs
 */
public enum Languages {
    EN("en");
    public final String language;
    Languages(String language) {
        this.language = language;
    }
    @Override
    public String toString(){
        return language;
    }
}
