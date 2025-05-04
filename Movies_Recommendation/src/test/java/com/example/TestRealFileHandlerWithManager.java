package com.example;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestRealFileHandlerWithManager {
    private List<String> validMovieData;
    private List<String> invalidMovieData;
    private List<String> validUserData;
    private List<String> invalidUserData;

    private FileHandler fileHandler;

    private MovieManager movieManager;
    private UserManager userManager;

    @Before
    public void setUp() throws IOException {
        // Initialize mock and managers
        fileHandler =new FileHandler();
        movieManager = new MovieManager();
        userManager = new UserManager();

        // Prepare valid movie  data
        validMovieData = fileHandler.readFile("validMovies.txt");
        // Prepare invalid movie  data
        invalidMovieData  = fileHandler.readFile("invalidMovies.txt");;

        // Prepare valid user data
        validUserData = fileHandler.readFile("validUsers.txt");

        // Prepare invalid user data
        invalidUserData = fileHandler.readFile("invalidUsers.txt");


    }

    @Test
    public void testValidLoadMoviesAndGetByGenre()  {
        boolean movieLoaded = movieManager.loadMovies(validMovieData);
        assertTrue("Movie loading failed", movieLoaded);

        String[]  actionMovies =  movieManager.getMoviesByGenre("horror").toArray(new String[0]);
        String[] expected = {"S201","TC201"};
        assertArrayEquals("Action genre movies mismatch", expected, actionMovies);
    }
    @Test
    public void testInvalidLoadMovies()  {

        boolean movieLoaded = movieManager.loadMovies(invalidMovieData);
        assertFalse("Invalid Movie data should fail validation", movieLoaded);


    }
    @Test
    public void testValidateUsersWithValidData()  {
        boolean usersValid = userManager.validateUsers(validUserData);
        assertTrue("Valid user data should pass validation", usersValid);
    }

    @Test
    public void testValidateUsersWithInvalidData() {
        boolean usersValid = userManager.validateUsers(invalidUserData);
        System.out.println(usersValid);
        assertFalse("Invalid user data should fail validation", usersValid);
    }
}
