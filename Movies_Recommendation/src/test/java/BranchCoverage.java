import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BranchCoverage {

    @Test
    void testValidateMovieTitle_Valid() {
        MovieManager mm = new MovieManager();
        assertTrue(mm.validateMovieTitle("Inception"));
    }

    @Test
    void testValidateMovieTitle_Invalid() {
        MovieManager mm = new MovieManager();
        String title = "Incepti*n";
        assertFalse(mm.validateMovieTitle(title));
        String expected = MovieManager.ERROR_MOVIE_TITLE.replace("{movie_title}", title);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void testValidateUserId_ValidAndInvalid() {
        UserManager um = new UserManager();

        String validId = "12345678A";
        assertTrue(um.validateUserId(validId));

        String invalidId = "1234A";
        assertFalse(um.validateUserId(invalidId));
        String expected = UserManager.ERROR_USER_ID.replace("{user_id}", invalidId);
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }
}
