import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class MovieManagerTest {

    private List<String> movieData = new ArrayList<>();
    private MovieManager movieManager;

    @Before
    public void init(){
        movieManager = new MovieManager();
        try {
            movieData = FileHandler.readFile("movies.txt");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_getGenre()
    {
        boolean result = movieManager.loadMovies(movieData);
        assertTrue(result);

        assertEquals(new HashSet<>(Arrays.asList("action", "thriller")), movieManager.getGenres("I124"));
    }

    @Test
    public void test_getMoviesByGenre()
    {
        boolean result = movieManager.loadMovies(movieData);
        assertTrue(result);

        assertTrue(movieManager.getMoviesByGenre("horror").contains("S201"));
    }

    @Test
    public void test_getTitle()
    {
        boolean result = movieManager.loadMovies(movieData);
        assertTrue(result);

        assertEquals("Toy Story", movieManager.getTitle("TS789"));
    }

}