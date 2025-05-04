package com.example;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestFileHandlerMockWithManager {

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
        fileHandler = mock(FileHandler.class);
        movieManager = new MovieManager();
        userManager = new UserManager();

        // Prepare valid movie mock data
        validMovieData = Arrays.asList(
                "The Matrix,TM123",
                "action,sci-fi",
                "Inception,I124",
                "action,thriller",
                "Toy Story,TS78",
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

        // Default behavior for tests (can be overridden per test)
        when(fileHandler.readFile("movie")).thenReturn(validMovieData);
        when(fileHandler.readFile("user")).thenReturn(validUserData);
    }

    @Test
    public void testValidLoadMoviesAndGetByGenre() throws IOException {
        boolean movieLoaded = movieManager.loadMovies(fileHandler.readFile("movie"));
        assertTrue("Movie loading failed", movieLoaded);

        String[]  actionMovies =  movieManager.getMoviesByGenre("action").toArray(new String[0]);
        String[] expected = {"TM123", "I124"};
        assertArrayEquals("Action genre movies mismatch", expected, actionMovies);
    }
    @Test
    public void testInvalidLoadMovies() throws IOException {
        when(fileHandler.readFile("movie")).thenReturn(invalidMovieData);
        boolean movieLoaded = movieManager.loadMovies(fileHandler.readFile("movie"));
        assertFalse("Invalid Movie data should fail validation", movieLoaded);


    }
    @Test
    public void testValidateUsersWithValidData() throws IOException {
        boolean usersValid = userManager.validateUsers(fileHandler.readFile("user"));
        assertTrue("Valid user data should pass validation", usersValid);
    }

    @Test
    public void testValidateUsersWithInvalidData() throws IOException {
        when(fileHandler.readFile("user")).thenReturn(invalidUserData);
        boolean usersValid = userManager.validateUsers(fileHandler.readFile("user"));
        assertFalse("Invalid user data should fail validation", usersValid);
    }
}
