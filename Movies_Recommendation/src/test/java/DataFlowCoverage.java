// DataFlowCoverage.java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataFlowCoverage {

    // DU Pairs Table:
    // Variable         | Definition → Use Path
    // -----------------|-------------------------
    // expectedPrefix   | def@N2 → use@N3
    // idPrefix         | def@N2 → use@N3
    // idDigits         | def@N2 → use@N4, use@N6 (loop)
    // uniqueDigits     | def@N5 → use@N7 (add)
    // c (loop var)     | def@N6 → use@N7

    @Test
    void testDefUse_expectedPrefix() {
        MovieManager mm = new MovieManager();
        assertTrue(mm.validateMovieId("I123", "Inception"));
    }

    @Test
    void testDefUse_idPrefix() {
        MovieManager mm = new MovieManager();
        assertFalse(mm.validateMovieId("XYZ123", "Inception"));
        String expected = MovieManager.ERROR_MOVIE_ID_LETTERS.replace("{movie_id}", "XYZ123");
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void testDefUse_idDigitsLength() {
        MovieManager mm = new MovieManager();
        assertFalse(mm.validateMovieId("I12", "Inception"));
        String expected = MovieManager.ERROR_MOVIE_ID_DIGITS_COUNT.replace("{movie_id}", "I12");
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void testDefUse_idDigitsLoopUse() {
        MovieManager mm = new MovieManager();
        assertTrue(mm.validateMovieId("I321", "Inception"));
    }

    @Test
    void testDefUse_uniqueDigitsSet_AddFails() {
        MovieManager mm = new MovieManager();
        assertFalse(mm.validateMovieId("I121", "Inception"));
        String expected = MovieManager.ERROR_MOVIE_ID_NUMBERS.replace("{movie_id}", "I121");
        assertEquals(expected, FileHandler.readFirstLine("recommendations.txt"));
    }

    @Test
    void testDefUse_loopVariableC() {
        MovieManager mm = new MovieManager();
        assertTrue(mm.validateMovieId("I456", "Inception"));
    }
}
