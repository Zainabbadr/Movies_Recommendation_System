import java.util.*;
import java.io.*;

public class MovieManager {
    private Map<String, Set<String>> movieGenres = new HashMap<>();
    private Map<String, String> movieTitles = new HashMap<>();
    private Map<String, Set<String>> genreToMovies = new HashMap<>();

    public boolean loadMovies(List<String> movieData) {
        for (int i = 0; i < movieData.size(); i += 2) {
            String[] info = movieData.get(i).split(",");
            if (info.length < 2) continue;
            String title = info[0];
            String id = info[1];

            if (!title.matches("([A-Z][a-z]*)( [A-Z][a-z]*)*")) {
                FileHandler.writeFile("recommendations.txt", "ERROR: Movie Title " + title + " is wrong");
                return false;
            }

            String expectedPrefix = title.replaceAll("[^A-Z]", "");
            String idPrefix = id.replaceAll("\\d", "");
            String digits = id.replaceAll("\\D", "");
            Set<String> uniqueDigits = new HashSet<>(Arrays.asList(digits.split("")));

            if (!idPrefix.equals(expectedPrefix)) {
                FileHandler.writeFile("recommendations.txt", "ERROR: Movie Id letters " + id + " are wrong");
                return false;
            }

            if (uniqueDigits.size() != digits.length()) {
                FileHandler.writeFile("recommendations.txt", "ERROR: Movie Id numbers " + id + " aren’t unique");
                return false;
            }

            movieTitles.put(id, title);
            for (String genre : movieData.get(i + 1).split(",")) {
                movieGenres.computeIfAbsent(id, k -> new HashSet<>()).add(genre);
                genreToMovies.computeIfAbsent(genre, k -> new HashSet<>()).add(id);
            }
        }
        return true;
    }

    public Set<String> getGenres(String movieId) {
        return movieGenres.getOrDefault(movieId, new HashSet<>());
    }

    public Set<String> getMoviesByGenre(String genre) {
        return genreToMovies.getOrDefault(genre, new HashSet<>());
    }

    public String getTitle(String movieId) {
        return movieTitles.get(movieId);
    }
}
