import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MovieManagerTest {

    private MovieManager movieManager;

    @Before
    public void set_up()
    {
        movieManager = new MovieManager();
    }

    @Test
    public void validTitle_singleWord()
    {
        String movieTitle = "Inception";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertTrue("Method should return true for single word title",result);
    }

    @Test
    public void invalidTitle_startsLowerCase()
    {
        String movieTitle = "inception";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertFalse("Method should return false for title starts with lower case", result);
    }

    @Test
    public void validTitle_multipleWords()
    {
        String movieTitle = "Lord Of The Rings";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertTrue("Method should return true for multiple word title ",result);
    }

    @Test
    public void invalidTitle_hasSpecialChars()
    {
        String movieTitle = "Incep@tion";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertFalse("Method should return false for title contains special characters", result);
    }

    @Test
    public void invalidTitle_noSpacing()
    {
        String movieTitle = "ToyStory";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertFalse("Method should return false for no spacing title", result);
    }

    @Test
    public void invalidTitle_emptyTitle()
    {
        String movieTitle = "";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertFalse("Method should return false for empty title", result);
    }
    @Test
    public void invalidTitle_middleWordStartsLowercase()
    {
        String movieTitle = "The dark Knight";
        boolean result = movieManager.validateMovieTitle(movieTitle);
        assertFalse("Method should return false for title's middle word starts with lower case", result);
    }

    @Test
    public void validID()
    {
        String id = "TS123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertTrue("Method should return true for valid id",result);
    }

    @Test
    public void invalidID_nonUniqueNumbers()
    {
        String id = "TS113";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Method should return false for non unique numbers id",result);
    }

    @Test
    public void invalidID_lowerCaseLetters()
    {
        String id = "tS123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Method should return false for lower case prefix",result);
    }
    @Test
    public void invalidID_onlyLetters()
    {
        String id = "TS";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Method should return false for non 3 digit id",result);
    }

    @Test
    public void invalidID_onlyNumbers()
    {
        String id = "123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Method should return false for missing prefix id",result);
    }

    @Test
    public void invalidID_wrongPrefix()
    {
        String id = "AB123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Method should return false for wrong prefix id",result);
    }

    @Test
    public void invalidID_specialChar()
    {
        String id = "TS@123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Method should return false for id contains special character",result);
    }

    @Test
    public void invalidID_containSpace()
    {
        String id = "TS 123";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Method should return false for id contains spaces",result);
    }
    @Test
    public void invalidID_moreThan3Digits()
    {
        String id = "TS1234";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Method should return false for non 3 digit id",result);
    }
    @Test
    public void invalidID_missingDigits()
    {
        String id = "TS12";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Method should return false for non 3 digit id",result);
    }
    @Test
    public void invalidID_emptyID()
    {
        String id = "";
        String title = "Toy Story";

        boolean result = movieManager.validateMovieId(id, title);
        assertFalse("Method should return false for empty id",result);
    }
    @Test
    public void test_getGenre()
    {
        movieManager.movieData=Arrays.asList("Inception,I124",
                "action,thriller");
        movieManager.loadMovies();
        assertEquals(new HashSet<>(Arrays.asList("action", "thriller")), movieManager.getGenres("I124"));
    }

    @Test
    public void test_getMoviesByGenre()
    {
        movieManager.movieData= Arrays.asList("Inception,I124",
                "action,thriller");
        boolean result = movieManager.loadMovies();
        assertTrue(result);
        assertTrue(movieManager.getMoviesByGenre("action").contains("I124"));
    }
    @Test
    public void test_getTitle()
    {
        movieManager.movieData = Arrays.asList("Inception,I124",
                "action,thriller");
        boolean result = movieManager.loadMovies();
        assertTrue(result);

        assertEquals("Inception", movieManager.getTitle("I124"));
    }
    @Test
    public void testLoadMovies_validData()
    {
        movieManager.movieData = Arrays.asList(
                "The Matrix,TM123" ,
                "action,sci-fi" ,
                "Inception,I124" ,
                "action,thriller"
        );
        boolean result = movieManager.loadMovies();
        assertTrue("Method should return false for invalid movie data", result);
        assertEquals(new HashSet<>(Arrays.asList("action", "thriller")), movieManager.getGenres("I124"));
        assertTrue(movieManager.getMoviesByGenre("action").contains("TM123"));
        assertEquals("Inception", movieManager.getTitle("I124"));
    }
    @Test
    public void testLoadMovies_invalidTitle()
    {
        movieManager.movieData = Arrays.asList(
                "The matrix,TM123" ,
                "action,sci-fi"
        );
        boolean result = movieManager.loadMovies();
        assertFalse("Method should return false for invalid movie title",result);
    }
    @Test
    public void testLoadMovies_wrongPrefixID()
    {
        movieManager.movieData= Arrays.asList(
                "The matrix,I123" ,
                "action,sci-fi"
        );
        boolean result = movieManager.loadMovies();
        assertFalse("Method should return false for wrong prefix id", result);
    }
    @Test
    public void testLoadMovies_nonUniqueNumberID()
    {
        movieManager.movieData = Arrays.asList(
                "The matrix,TM113" ,
                "action,sci-fi"
        );
        boolean result = movieManager.loadMovies();
        assertFalse("Method should return false for non unique numbers id", result);
    }
    @Test
    public void testLoadMovies_emptyList()
    {
        movieManager.movieData = new ArrayList<>();
        boolean result = movieManager.loadMovies();
        assertTrue("Method should return true for empty list ", result);
    }

    //Test FileHandler to verify error messages with mocking
    @Test
    public void testFileHandlerCalled_invalidTitle()
    {
        try(MockedStatic<FileHandler> mockedFileHandler = Mockito.mockStatic(FileHandler.class))
        {
            String movieTitle = "The matrix";
            movieManager.validateMovieTitle(movieTitle);

            mockedFileHandler.verify(() ->
                    FileHandler.writeFile(eq("recommendations.txt"),contains("ERROR: Movie Title The matrix is wrong")));
        }
    }
    @Test
    public void testFileHandlerCalled_invalidPrefixID()
    {
        try(MockedStatic<FileHandler> mockedFileHandler = Mockito.mockStatic(FileHandler.class))
        {
            String movieID = "I123";
            String movieTitle = "The Matrix";
            movieManager.validateMovieId(movieID, movieTitle);

            mockedFileHandler.verify(() ->
                    FileHandler.writeFile(eq("recommendations.txt"),contains("ERROR: Movie Id letters I123 are wrong")));
        }
    }
    @Test
    public void testFileHandlerCalled_invalidDigitsID() {
        try (MockedStatic<FileHandler> mockedFileHandler = Mockito.mockStatic(FileHandler.class)) {
            String movieID = "TM111";
            String movieTitle = "The Matrix";
            movieManager.validateMovieId(movieID, movieTitle);

            mockedFileHandler.verify(() ->
                    FileHandler.writeFile(eq("recommendations.txt"), contains("ERROR: Movie Id numbers TM111 aren’t unique")));
        }
    }
}