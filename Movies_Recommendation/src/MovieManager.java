import java.util.*;

public class MovieManager {
    private Map<String, Set<String>> movieGenres = new HashMap<>();
    private Map<String, String> movieTitles = new HashMap<>();
    private Map<String, Set<String>> genreToMovies = new HashMap<>();

    public boolean loadMovies(List<String> movieData) {
        for (int i = 0; i < movieData.size(); i += 2) {
            String[] movieInfo = movieData.get(i).split(",");
            String[] genres = movieData.get(i + 1).split(",");
            String title = movieInfo[0];
            String id = movieInfo[1];

            if (!validateMovieTitle(title) || !validateMovieId(id, title)) {
                return false;
            }

            movieTitles.put(id, title);
            for (String genre : genres) {
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

    private boolean validateMovieTitle(String title) {
        return title.matches("([A-Z][a-z]*)( [A-Z][a-z]*)*");
    }

    private boolean validateMovieId(String movieId, String title) {
        String expectedPrefix = title.replaceAll("[^A-Z]", "");
        String idPrefix = movieId.replaceAll("\\d", "");
        String idNumbers = movieId.replaceAll("\\D", "");
        if (!idPrefix.equals(expectedPrefix)) return false;
        return new HashSet<>(Arrays.asList(idNumbers.split(""))).size() == idNumbers.length();
    }
}
