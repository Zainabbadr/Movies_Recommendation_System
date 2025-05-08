// BranchCoverage.java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BranchCoverage {

    @Test
    void testValidMovieId_AllBranchesPass() {
        MovieManager mm = new MovieManager();
        String movieId = "ABC123";
        String title = "Awesome Big Cinema";
        assertTrue(mm.validateMovieId(movieId, title));
    }

    @Test
    void testInvalidPrefix() {
        MovieManager mm = new MovieManager();
        String movieId = "XYZ123";
        String title = "Awesome Big Cinema";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_LETTERS.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void testTooFewDigits() {
        MovieManager mm = new MovieManager();
        String movieId = "ABC12";
        String title = "Awesome Big Cinema";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_DIGITS_COUNT.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void testTooManyDigits() {
        MovieManager mm = new MovieManager();
        String movieId = "ABC1234";
        String title = "Awesome Big Cinema";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_DIGITS_COUNT.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void testDuplicateDigits() {
        MovieManager mm = new MovieManager();
        String movieId = "ABC121";
        String title = "Awesome Big Cinema";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_NUMBERS.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }
}
