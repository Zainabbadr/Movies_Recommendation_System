import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class BasisPathCoverage {

    @Test
    void testRecommend_NoLikedMovies() {
        MovieManager mm = new MovieManager();
        UserManager um = new UserManager();
        RecommendationManager rm = new RecommendationManager(mm, um);

        um.userData = Arrays.asList("John Doe,12345678A", "");
        mm.movieData = Arrays.asList("Inception,I123", "action,thriller");

        List<String> result = rm.recommend();
        assertNotNull(result);
        assertTrue(result.contains("No Recommendations"));
    }

    @Test
    void testRecommend_GenreMatch() {
        MovieManager mm = new MovieManager();
        UserManager um = new UserManager();
        RecommendationManager rm = new RecommendationManager(mm, um);

        um.userData = Arrays.asList("Alice,12345678A", "I123");
        mm.movieData = Arrays.asList("Inception,I123", "action,thriller",
                                    "Matrix,M456", "action,sci-fi");

        List<String> result = rm.recommend();
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.get(2).contains("Matrix"));
    }
}
