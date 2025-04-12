import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieValidationTest {
    private MovieValidation movieValidation;

    @Before
    public void init()
    {
        movieValidation = new MovieValidation();
    }
    @Test
    public void validTitle_SingleWord()
    {
      String movieTitle = "Inception";
      boolean result = movieValidation.validateMovieTitle(movieTitle);
      assertTrue("Result should be True",result);
    }

    @Test
    public void invalidTitle_StartsLowerCase()
    {
        String movieTitle = "inception";
        boolean result = movieValidation.validateMovieTitle(movieTitle);
        assertFalse("Result should be false", result);
    }

    @Test
    public void validTitle_MultipleWords()
    {
        String movieTitle = "Lord Of The Rings";
        boolean result = movieValidation.validateMovieTitle(movieTitle);
        assertTrue("Result should be True",result);
    }

    @Test
    public void invalidTitle_HasSpecialChars()
    {
        String movieTitle = "Incep@tion";
        boolean result = movieValidation.validateMovieTitle(movieTitle);
        assertFalse("Result should be false", result);
    }

    @Test
    public void invalidTitle_NoSpacing()
    {
        String movieTitle = "ToyStory";
        boolean result = movieValidation.validateMovieTitle(movieTitle);
        assertFalse("Result should be false", result);
    }

    @Test
    public void invalidTitle_EmptyTitle()
    {
        String movieTitle = "";
        boolean result = movieValidation.validateMovieTitle(movieTitle);
        assertFalse("Result should be false", result);
    }
    @Test
    public void invalidTitle_MiddleWordStartsLowercase()
    {
        String movieTitle = "The dark Knight";
        boolean result = movieValidation.validateMovieTitle(movieTitle);
        assertFalse("Result should be false", result);
    }

    @Test
    public void validID()
    {
        String id = "TS123";
        String title = "Toy Story";

        boolean result = movieValidation.validateMovieId(id, title);
        assertTrue("Result should be True",result);
    }

    @Test
    public void invalidID_NonUniqueNumbers()
    {
        String id = "TS113";
        String title = "Toy Story";

        boolean result = movieValidation.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }

    @Test
    public void invalidID_LowerCaseLetters()
    {
        String id = "tS123";
        String title = "Toy Story";

        boolean result = movieValidation.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }
    @Test
    public void invalidID_OnlyLetters()
    {
        String id = "TS";
        String title = "Toy Story";

        boolean result = movieValidation.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }

    @Test
    public void invalidID_OnlyNumbers()
    {
        String id = "123";
        String title = "Toy Story";

        boolean result = movieValidation.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }

    @Test
    public void invalidID_WrongPrefix()
    {
        String id = "AB123";
        String title = "Toy Story";

        boolean result = movieValidation.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }

    @Test
    public void invalidID_SpecialChar()
    {
        String id = "TS@123";
        String title = "Toy Story";

        boolean result = movieValidation.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }

    @Test
    public void invalidID_ContainSpace()
    {
        String id = "TS 123";
        String title = "Toy Story";

        boolean result = movieValidation.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }
    @Test
    public void invalidID_MoreThan3Digits()
    {
        String id = "TS1234";
        String title = "Toy Story";

        boolean result = movieValidation.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }
    @Test
    public void invalidID_MissingDigits()
    {
        String id = "TS12";
        String title = "Toy Story";

        boolean result = movieValidation.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }
    @Test
    public void invalidID_emptyID()
    {
        String id = "";
        String title = "Toy Story";

        boolean result = movieValidation.validateMovieId(id, title);
        assertFalse("Result should be False",result);
    }
}