

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class IntegrationTesting {

    private FileHandler fileHandler;

    private MovieManager movieManager;
    private UserManager userManager;

    @Before
    public void setUp() throws IOException {
        // Initialize mock and managers
        fileHandler =new FileHandler();
        movieManager = new MovieManager(fileHandler,"movies.txt");
        userManager = new UserManager(fileHandler,"users.txt");
        userManager.readUsers();
        movieManager.readMovies();
    }
@Test
    public void validateMovieDataFromFileHandler(){
        String[] expected={
                "The Matrix,TM123",
                "action,sci-fi",
                "Inception,I124",
                "action,thriller",
                "Toy Story,TS781",
                "animation,comedy",
                "Up,U423",
                "animation",
                "The Conjuring,TC201",
                "horror",
                "Interstellar,I142",
                "sci-fi,drama",
                "Saw,S201",
                "horror",
                "The Bear,TB789",
                "comedy"
        };
        String[] actual=movieManager.movieData.toArray(new String[0]);
        Assert.assertArrayEquals("movies mismatch", expected, actual);
    }
    @Test
    public void testValidLoadMoviesAndGetByGenreFromFileHandler()  {
        boolean movieLoaded = movieManager.loadMovies();
        Assert.assertTrue("Movie loading failed", movieLoaded);
        String[]  actionMovies =  movieManager.getMoviesByGenre("horror").toArray(new String[0]);
        String[] expected = {"S201","TC201"};
        Assert.assertArrayEquals("Action genre movies mismatch", expected, actionMovies);
    }
    @Test
    public void testInvalidLoadMoviesFromFileHandler()  {
        movieManager.filename="invalidMovies.txt";
        movieManager.readMovies();
        boolean movieLoaded = movieManager.loadMovies();
        Assert.assertFalse("Invalid Movie data should fail validation", movieLoaded);
    }
    @Test
    public void validateUserDataFromFileHandler(){
        String[] expected={
                "Alice Smith,12345678A",
                "TM123,I142",
                "Bob Brown,87654321B",
                "TS789"
        };
        String[] actual=userManager.userData.toArray(new String[0]);
        Assert.assertArrayEquals("users mismatch", expected, actual);
    }
    @Test
    public void testValidateUsersWithValidDataFromFileHandler()  {
        boolean usersValid = userManager.validateUsers();
        Assert.assertTrue("Valid user data should pass validation", usersValid);
    }

    @Test
    public void testValidateUsersWithInvalidDataFromFileHandler() {
        userManager.filename="invalidUsers.txt";
        userManager.readUsers();
        boolean usersValid = userManager.validateUsers();
        Assert.assertFalse("Invalid user data should fail validation", usersValid);
    }
    @Test
    public void testValidateUsersIntegration() {

        RecommendationManager reccomendManager=new RecommendationManager(movieManager,userManager);
        //actual
        String[] actual={
                "Alice Smith,12345678A",
                "TM123,I142",
                "Bob Brown,87654321B",
                "TS789"
        };
        // expected
        List<String> result = reccomendManager.getUsers();
        String[] expected=result.toArray(new String[0]);
        // Assert
        Assert.assertArrayEquals("output mismatch",expected,actual);
    }
    @Test
    public void testValidateMoviesIntegration() {

        RecommendationManager reccomendManager=new RecommendationManager(movieManager,userManager);
        //actual
        String[] actual={
                "The Matrix,TM123",
                "action,sci-fi",
                "Inception,I124",
                "action,thriller",
                "Toy Story,TS781",
                "animation,comedy",
                "Up,U423",
                "animation",
                "The Conjuring,TC201",
                "horror",
                "Interstellar,I142",
                "sci-fi,drama",
                "Saw,S201",
                "horror",
                "The Bear,TB789",
                "comedy"
        };
        // expected
        List<String> result = reccomendManager.getMovies();
        String[] expected=result.toArray(new String[0]);
        // Assert
        Assert.assertArrayEquals("output mismatch",expected,actual);
    }


    @Test
    public void testOutput() {
        RecommendationManager recommendationEngine=new RecommendationManager(movieManager,userManager);
        String[] expectedOutput={
                "Alice Smith,12345678A",
                "Inception",
                "Bob Brown,87654321B",
                "No Recommendations"
        };
        List<String> output= recommendationEngine.recommend();
        String[] actualOutput=output.toArray(new String[0]);
        Assert.assertArrayEquals("output mismatch", expectedOutput, actualOutput);

    }
    @Test
    public void testWriteOutputWithFileHandler() {
        String[] actualOutput = null;
        RecommendationManager recommendationEngine=new RecommendationManager(movieManager,userManager);
        List<String> output= recommendationEngine.recommend();
        String concatenatedOutput = String.join("\n", output);
        fileHandler.writeFile("test.txt", concatenatedOutput);
        try {
            actualOutput = fileHandler.readFile("test.txt").toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] expectedOutput={
                "Alice Smith,12345678A",
                "Inception",
                "Bob Brown,87654321B",
                "No Recommendations"
        };
        Assert.assertArrayEquals("output mismatch", expectedOutput, actualOutput);

    }
}
