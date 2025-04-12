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
    @Test
    public void validTitle_SingleWord()
    {
        String movieTitle = "Inception";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertTrue("Result should be True",result);
    }

    @Test
    public void invalidTitle_StartsLowerCase()
    {
        String movieTitle = "inception";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertFalse("Result should be false", result);
    }

    @Test
    public void validTitle_MultipleWords()
    {
        String movieTitle = "Lord Of The Rings";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertTrue("Result should be True",result);
    }

    @Test
    public void invalidTitle_HasSpecialChars()
    {
        String movieTitle = "Incep@tion";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertFalse("Result should be false", result);
    }

    @Test
    public void invalidTitle_NoSpacing()
    {
        String movieTitle = "ToyStory";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertFalse("Result should be false", result);
    }

    @Test
    public void invalidTitle_EmptyTitle()
    {
        String movieTitle = "";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertFalse("Result should be false", result);
    }
    @Test
    public void invalidTitle_MiddleWordStartsLowercase()
    {
        String movieTitle = "The dark Knight";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertFalse("Result should be false", result);
    }

    @Test
    public void validID()
    {
        String id = "TS123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertTrue("Result should be True",result);
    }

    @Test
    public void invalidID_NonUniqueNumbers()
    {
        String id = "TS113";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }

    @Test
    public void invalidID_LowerCaseLetters()
    {
        String id = "tS123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }
    @Test
    public void invalidID_OnlyLetters()
    {
        String id = "TS";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }

    @Test
    public void invalidID_OnlyNumbers()
    {
        String id = "123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }

    @Test
    public void invalidID_WrongPrefix()
    {
        String id = "AB123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }

    @Test
    public void invalidID_SpecialChar()
    {
        String id = "TS@123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }

    @Test
    public void invalidID_ContainSpace()
    {
        String id = "TS 123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }
    @Test
    public void invalidID_MoreThan3Digits()
    {
        String id = "TS1234";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }
    @Test
    public void invalidID_MissingDigits()
    {
        String id = "TS12";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }
    @Test
    public void invalidID_emptyID()
    {
        String id = "";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }

}