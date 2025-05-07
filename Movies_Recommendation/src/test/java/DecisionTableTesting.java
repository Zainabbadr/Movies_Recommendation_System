import org.junit.Assert;
import org.junit.Before;

import java.io.IOException;

public class DecisionTableTesting {
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

    public void AllCorrectTest(){
        String[] expected={
                "Alice Smith,12345678A",
                "Inception",
                "Bob Brown,87654321B",
                "The Bear,Up"
        };
        String[] actual=movieManager.movieData.toArray(new String[0]);
        Assert.assertArrayEquals("movies mismatch", expected, actual);
    }
}
