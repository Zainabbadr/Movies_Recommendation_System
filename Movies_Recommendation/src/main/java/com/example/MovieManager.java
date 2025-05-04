package com.example;
import java.util.*;
import java.io.*;

public class MovieManager {
    private final Map<String, Set<String>> movieGenres = new HashMap<>();
    private final Map<String, String> movieTitles = new HashMap<>();
    private final Map<String, Set<String>> genreToMovies = new HashMap<>();

    public boolean loadMovies(List<String> movieData) {
        for (int i = 0; i < movieData.size(); i += 2) {
            String[] info = movieData.get(i).split(",");
            if (info.length < 2) continue;

            String title = info[0];
            String movieId = info[1];

            if (!validateMovieTitle(title)) return false;
            if (!validateMovieId(movieId, title)) return false;

            movieTitles.put(movieId, title);

            String[] genres = movieData.get(i + 1).split(",");
            for (String genre : genres) {
                movieGenres.computeIfAbsent(movieId, k -> new HashSet<>()).add(genre);
                genreToMovies.computeIfAbsent(genre, k -> new HashSet<>()).add(movieId);
            }
        }
        return true;
    }

    public boolean validateMovieTitle(String title) {
        if (!title.matches("([A-Z][a-z]*)( [A-Z][a-z]*)*")) {
            FileHandler.writeFile("recommendations.txt", "ERROR: Movie Title " + title + " is wrong");
            return false;
        }
        return true;
    }

    public boolean validateMovieId(String movieId, String title) {
        String expectedPrefix = title.replaceAll("[^A-Z]", "");
        String idPrefix = movieId.replaceAll("\\d", "");
        String idDigits = movieId.replaceAll("\\D", "");

        if (!idPrefix.equals(expectedPrefix)) {
            FileHandler.writeFile("recommendations.txt", "ERROR: Movie Id letters " + movieId + " are wrong");
            return false;
        }

        Set<Character> uniqueDigits = new HashSet<>();
        for (char c : idDigits.toCharArray()) {
            if (!uniqueDigits.add(c)) {
                FileHandler.writeFile("recommendations.txt", "ERROR: Movie Id numbers " + movieId + " aren’t unique");
                return false;
            }
        }

        return true;
    }

    public Set<String> getGenres(String movieId) {
        return movieGenres.getOrDefault(movieId, Collections.emptySet());
    }

    public Set<String> getMoviesByGenre(String genre) {
        return genreToMovies.getOrDefault(genre, Collections.emptySet());
    }

    public String getTitle(String movieId) {
        return movieTitles.get(movieId);
    }
}
