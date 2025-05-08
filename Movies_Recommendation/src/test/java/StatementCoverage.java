// StatementCoverage.java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatementCoverage {

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
}