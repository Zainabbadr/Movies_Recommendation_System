import java.util.Arrays;
import java.util.HashSet;

public class MovieValidation {

    public boolean validateMovieTitle(String title) {
        return title.matches("([A-Z][a-z]*)( [A-Z][a-z]*)*");
    }

    public boolean validateMovieId(String movieId, String title) {
        String expectedPrefix = title.replaceAll("[^A-Z]", "");
        String idPrefix = movieId.replaceAll("\\d", "");
        String idNumbers = movieId.replaceAll("\\D", "");
        if (!idPrefix.equals(expectedPrefix)) return false;
        return new HashSet<>(Arrays.asList(idNumbers.split(""))).size() == idNumbers.length();
    }
}
