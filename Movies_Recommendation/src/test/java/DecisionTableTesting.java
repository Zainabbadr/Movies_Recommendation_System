import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class DecisionTableTesting {
    private FileHandler fileHandler;

    private MovieManager movieManager;
    private UserManager userManager;
    private RecommendationManager recommendationManager;

    @Before
    public void setUp() throws IOException {
        // Initialize mock and managers
        fileHandler =new FileHandler();
        movieManager = new MovieManager(fileHandler,"movies.txt");
        userManager = new UserManager(fileHandler,"users.txt");
        recommendationManager = new RecommendationManager(movieManager, userManager);
        userManager.readUsers();
        movieManager.readMovies();
    }

//    @Test
//    public void AllCorrectTest(){
//        String[] expected={
//                "Alice Smith,12345678A",
//                "Inception",
//                "Bob Brown,87654321B",
//                "The Bear,Up"
//        };
//        String[] actual=recommendationManager.recommend().toArray(new String[0]);
//        Assert.assertArrayEquals("movies mismatch", expected, actual);
//    }
//    @Test
//    public void UserIdTest(){
//        String[] expected={
//                "ERROR: User Id 1234567A is wrong"
//        };
//        String[] actual= null;
//        try {
//            recommendationManager.recommend();
//            actual = fileHandler.readFile("recommendations.txt").toArray(new String[0]);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        Assert.assertArrayEquals("Error in User ID", expected, actual);
//    }

    @Test
    public void MovieIdTest() throws IOException {
        String[] expected={
                "ERROR: Movie Id digits in TS78 must be exactly 3"
        };
        String[] actual= null;
        try {
            actual = fileHandler.readFile("recommendations.txt").toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertArrayEquals("Error in Movie ID", expected, actual);
    }
//
//    @Test
//    public void NoMatchingMoviesTest(){
//        String[] expected={
//                "Alice Smith,12345678A",
//                "No Recommendations",
//                "Bob Brown,87654321B",
//                "The Bear,Up"
//        };
//        String[] actual=recommendationManager.recommend().toArray(new String[0]);
//        Assert.assertArrayEquals("Error in Movie ID", expected, actual);
//    }
}
