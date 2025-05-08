import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.*;


public class TestingWithFileHanlderMock {

    private List<String> validMovieData;
    private List<String> invalidMovieData;
    private List<String> validUserData;
    private List<String> invalidUserData;

    private FileHandler fileHandlerMock;

    private MovieManager movieManager;
    private UserManager userManager;

    @Before
    public void setUp() throws IOException {
        // Initialize mock and managers
        fileHandlerMock = Mockito.mock(FileHandler.class);


        // Prepare valid movie mock data
        validMovieData = Arrays.asList(
                "The Matrix,TM123",
                "action,sci-fi",
                "Inception,I124",
                "action,thriller",
                "Toy Story,TS781",
                "animation,comedy"
        );
        // Prepare invalid movie mock data
        invalidMovieData = Arrays.asList(
                "The Matrix,M123",
                "action,sci-fi",
                "Inception,I124",
                "action,thriller",
                "Toy Story,TS78",
                "animation,comedy"
        );

        // Prepare valid user data
        validUserData = Arrays.asList(
                "Alice Smith,12345678A",
                "TM123",
                "Bob Brown,87654321B",
                "TS789"
        );

        // Prepare invalid user data
        invalidUserData = Arrays.asList(
                "Alice Smith,12345678",  // Invalid ID
                "TM123",
                "Bob Brown,87654321B",
                "TS789"
        );

        // Configure default returns
        Mockito.when(fileHandlerMock.readFile("movie")).thenReturn(validMovieData);
        Mockito.when(fileHandlerMock.readFile("user")).thenReturn(validUserData);

        // CRUCIAL: Inject the mock into both managers
        movieManager = new MovieManager(fileHandlerMock, "movie");
        userManager = new UserManager(fileHandlerMock, "user");

    }

    @Test
    public void testValidLoadMoviesAndGetByGenre() {
        movieManager.readMovies();
        boolean movieLoaded = movieManager.loadMovies();
        Assert.assertTrue("Movie loading failed", movieLoaded);

        String[]  actionMovies =  movieManager.getMoviesByGenre("action").toArray(new String[0]);
        String[] expected = {"TM123", "I124"};
        Assert.assertArrayEquals("Action genre movies mismatch", expected, actionMovies);
    }
    @Test
    public void testInvalidLoadMovies() throws IOException {
        Mockito.when(fileHandlerMock.readFile("movie")).thenReturn(invalidMovieData);
        movieManager.readMovies();
        boolean movieLoaded = movieManager.loadMovies();
        Assert.assertFalse("Invalid Movie data should fail validation", movieLoaded);


    }
    @Test
    public void testValidateUsersWithValidData()  {
        userManager.readUsers();
        boolean usersValid = userManager.validateUsers();
        Assert.assertTrue("Valid user data should pass validation", usersValid);
    }

    @Test
    public void testValidateUsersWithInvalidData() throws IOException {
        Mockito.when(fileHandlerMock.readFile("user")).thenReturn(invalidUserData);
        userManager.readUsers();
        boolean usersValid = userManager.validateUsers();
        Assert.assertFalse("Invalid user data should fail validation", usersValid);
    }
}
