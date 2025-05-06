import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecommendationEngineTest {

    // Enable ByteBuddy experimental mode for Java 23 compatibility
    static {
        System.setProperty("net.bytebuddy.experimental", "true");
    }

    private MovieManager movieManager;
    private UserManager userManager;
    private BufferedWriter mockWriter;

    // Custom RecommendationEngine for testing that allows injecting mocks
    private static class TestableRecommendationEngine extends RecommendationEngine {
        public TestableRecommendationEngine(MovieManager movieManager, UserManager userManager) {
            super();
            this.movieManager = movieManager;
            this.userManager = userManager;
        }

        // Make fields accessible for the test
        private final MovieManager movieManager;
        private final UserManager userManager;
    }

    private TestableRecommendationEngine engine;

    @BeforeEach
    public void setUp() {
        // Create mocks manually instead of using annotations
        movieManager = mock(MovieManager.class);
        userManager = mock(UserManager.class);
        mockWriter = mock(BufferedWriter.class);
        engine = new TestableRecommendationEngine(movieManager, userManager);
    }

    @Test
    public void testLoadMovies_Success() {
        // Arrange
        List<String> movieData = Arrays.asList("Movie Title,MT123", "Action,Comedy");
        when(movieManager.loadMovies(movieData)).thenReturn(true);

        // Act
        boolean result = engine.loadMovies(movieData);

        // Assert
        assertTrue(result);
        verify(movieManager).loadMovies(movieData);
    }

    @Test
    public void testLoadMovies_Failure() {
        // Arrange
        List<String> movieData = Arrays.asList("Invalid Movie,IM123", "Drama");
        when(movieManager.loadMovies(movieData)).thenReturn(false);

        // Act
        boolean result = engine.loadMovies(movieData);

        // Assert
        assertFalse(result);
        verify(movieManager).loadMovies(movieData);
    }

    @Test
    public void testValidateUsers_Success() {
        // Arrange
        List<String> userData = Arrays.asList("John Doe,123456789", "MT123,AB456");
        when(userManager.validateUsers(userData)).thenReturn(true);

        // Act
        boolean result = engine.validateUsers(userData);

        // Assert
        assertTrue(result);
        verify(userManager).validateUsers(userData);
    }

    @Test
    public void testValidateUsers_Failure() {
        // Arrange
        List<String> userData = Arrays.asList("Invalid User,12345", "MT123");
        when(userManager.validateUsers(userData)).thenReturn(false);

        // Act
        boolean result = engine.validateUsers(userData);

        // Assert
        assertFalse(result);
        verify(userManager).validateUsers(userData);
    }

    @Test
    public void testGenerateRecommendations_WithRecommendations() throws IOException {
        // Using try-with-resources for MockedStatic
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            // Arrange
            List<String> userData = Arrays.asList(
                    "John Doe,123456789",
                    "MT123,AB456"
            );

            // Mock the movie genres and titles
            Set<String> mt123Genres = new HashSet<>(Arrays.asList("Action", "Comedy"));
            Set<String> ab456Genres = new HashSet<>(Arrays.asList("Drama", "Comedy"));

            Set<String> actionMovies = new HashSet<>(Arrays.asList("MT123", "XY789"));
            Set<String> comedyMovies = new HashSet<>(Arrays.asList("MT123", "AB456", "CD123"));
            Set<String> dramaMovies = new HashSet<>(Arrays.asList("AB456", "EF234"));

            when(movieManager.getGenres("MT123")).thenReturn(mt123Genres);
            when(movieManager.getGenres("AB456")).thenReturn(ab456Genres);

            when(movieManager.getMoviesByGenre("Action")).thenReturn(actionMovies);
            when(movieManager.getMoviesByGenre("Comedy")).thenReturn(comedyMovies);
            when(movieManager.getMoviesByGenre("Drama")).thenReturn(dramaMovies);

            when(movieManager.getTitle("CD123")).thenReturn("Comedy Drama");
            when(movieManager.getTitle("XY789")).thenReturn("Action Movie");
            when(movieManager.getTitle("EF234")).thenReturn("Drama Film");

            mockedFileHandler.when(() -> FileHandler.getBufferedWriter("recommendations.txt"))
                    .thenReturn(mockWriter);

            // Act
            engine.generateRecommendations(userData);

            // Assert - verify writer was called with user info
            verify(mockWriter).write("John Doe,123456789\n");

            // Verify some recommendations were written (using a more flexible verification)
            verify(mockWriter, times(2)).write(anyString());
        }
    }

    @Test
    public void testGenerateRecommendations_NoRecommendations() throws IOException {
        try (MockedStatic<FileHandler> mockedFileHandler = mockStatic(FileHandler.class)) {
            // Arrange
            List<String> userData = Arrays.asList(
                    "John Doe,123456789",
                    "MT123"
            );

            // Mock empty recommendations
            when(movieManager.getGenres("MT123")).thenReturn(Collections.emptySet());

            mockedFileHandler.when(() -> FileHandler.getBufferedWriter("recommendations.txt"))
                    .thenReturn(mockWriter);

            // Act
            engine.generateRecommendations(userData);

            // Assert
            verify(mockWriter).write("John Doe,123456789\n");
            verify(mockWriter).write("No Recommendations\n");
        }
    }
}
