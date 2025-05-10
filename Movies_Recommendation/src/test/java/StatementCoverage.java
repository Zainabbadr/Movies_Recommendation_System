// StatementCoverage.java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatementCoverage {
    @BeforeEach
    void clearFile() {
        FileHandler.writeFile("recommendations.txt", "");
    }

    @Test
    void testValidMovieTitle() {
        MovieManager mm = new MovieManager();
        assertTrue(mm.validateMovieTitle("Inception"), "Title with only letters should be valid");
    }

    @Test
    void testInvalidMovieTitle() {
        MovieManager mm = new MovieManager();
        String title = "Movie123";
        assertFalse(mm.validateMovieTitle(title), "Title with digits should be invalid");
        String expected = MovieManager.ERROR_MOVIE_TITLE.replace("{movie_title}", title);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }
    @Test
    void testInvalidPrefixInMovieId() {
        MovieManager mm = new MovieManager();
        String movieId = "XY123";
        String title = "Inception";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_LETTERS.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void testMovieIdTooFewDigits() {
        MovieManager mm = new MovieManager();
        String movieId = "I1";
        String title = "I";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_DIGITS_COUNT.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void testMovieIdHasDuplicateDigits() {
        MovieManager mm = new MovieManager();
        String movieId = "I122";
        String title = "I";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_NUMBERS.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void testValidMovieId() {
        MovieManager mm = new MovieManager();
        String movieId = "I123";
        String title = "Inception";
        assertTrue(mm.validateMovieId(movieId, title));
    }

    @Test
    void testMovieIdTooManyDigits() {
        MovieManager mm = new MovieManager();
        String movieId = "I1234"; // Now the prefix "I" matches title "Inception" → passes prefix check
        String title = "Inception";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_DIGITS_COUNT.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void testMovieIdTooShort() {
        MovieManager mm = new MovieManager();
        String movieId = "I1";
        String title = "Inception";
        assertFalse(mm.validateMovieId(movieId, title));
        String expected = MovieManager.ERROR_MOVIE_ID_DIGITS_COUNT.replace("{movie_id}", movieId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }
    @Test
    void testLikedMovieNotInList() {
        MovieManager mm = new MovieManager();
        String movieId = "M999";
        String title = "Nonexistent";
        assertFalse(mm.validateMovieId(movieId, title));
    }

}