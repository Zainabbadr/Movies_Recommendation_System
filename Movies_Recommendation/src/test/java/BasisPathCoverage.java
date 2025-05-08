// BasisPathCoverage.java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BasisPathCoverage {

    @Test
    void TC1_prefixMismatch() {
        MovieManager mm = new MovieManager();
        String movieId = "XYZ123";
        String title = "Inception";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_LETTERS.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void TC2_wrongDigitCount() {
        MovieManager mm = new MovieManager();
        String movieId = "I12";
        String title = "Inception";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_DIGITS_COUNT.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void TC3_duplicateDigits() {
        MovieManager mm = new MovieManager();
        String movieId = "I122";
        String title = "Inception";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_NUMBERS.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void TC4_validAllUniqueDigits() {
        MovieManager mm = new MovieManager();
        String movieId = "I123";
        String title = "Inception";
        assertTrue(mm.validateMovieId(movieId, title));
    }

    @Test
    void TC5_duplicateAfterTwoUniqueDigits() {
        MovieManager mm = new MovieManager();
        String movieId = "I121";
        String title = "Inception";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_NUMBERS.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void TC6_validDigitsInReverseOrder() {
        MovieManager mm = new MovieManager();
        String movieId = "I321";
        String title = "Inception";
        assertTrue(mm.validateMovieId(movieId, title));
    }
}
