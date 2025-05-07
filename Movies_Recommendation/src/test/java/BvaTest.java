import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BvaTest {

    UserManager um = new UserManager(new FileHandler(), "users.txt");
    @Test
    public void testUserId_TooShort() {
        boolean result = um.validateUserId("1234567A"); // only 8 chars
        assertFalse(result, "Expected invalid user ID (too short)");
    }
    @Test
    public void testUserId_Valid() {
        boolean result = um.validateUserId("12345678A"); // valid
        assertTrue(result, "Expected valid user ID");
    }
    @Test
    public void testUserId_TooLong() {
        boolean result = um.validateUserId("123456789A"); // too long
        assertFalse(result, "Expected invalid user ID (too long)");
    }

    MovieManager movieManager = new MovieManager(new FileHandler(), "movies.txt");
    @Test
    public void testValidMovieId() {

        assertTrue(movieManager.validateMovieId("U123", "Up"));
    }
    @Test
    public void testTooFewDigits() {

        assertFalse(movieManager.validateMovieId("U12", "Up"));
    }
    @Test
    public void testTooManyDigits() {

        assertFalse(movieManager.validateMovieId("U1234", "Up"));
    }
}
