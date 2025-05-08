import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FullPathCoverage {

    @Test
    void path1_prefixMismatch() {
        MovieManager mm = new MovieManager();
        String movieId = "XYZ123";
        String title = "Inception";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_LETTERS.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void path2_digitLengthMismatch() {
        MovieManager mm = new MovieManager();
        String movieId = "I12"; // only 2 digits
        String title = "Inception";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_DIGITS_COUNT.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void path3_duplicateDigits() {
        MovieManager mm = new MovieManager();
        String movieId = "I122"; // valid length, duplicate 2s
        String title = "Inception";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_NUMBERS.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void path4_allValid() {
        MovieManager mm = new MovieManager();
        String movieId = "I123"; // correct prefix, 3 unique digits
        String title = "Inception";
        assertTrue(mm.validateMovieId(movieId, title));
    }
}
