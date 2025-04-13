import java.io.IOException;
import java.util.List;

public class Main {
    private static final String MOVIE_FILE = "movies.txt";
    private static final String USER_FILE = "users.txt";

    public static void main(String[] args) {
        try {
            // Read data from files
            List<String> movieData = FileHandler.readFile(MOVIE_FILE);
            List<String> userData = FileHandler.readFile(USER_FILE);

            // Initialize the recommendation engine
            RecommendationEngine engine = new RecommendationEngine();

            // Load and validate data
            if (!engine.loadMovies(movieData)) {
                System.out.println("Error loading movies data");
                return;
            }

            if (!engine.validateUsers(userData)) {
                System.out.println("Error validating users data");
                return;
            }

            // Generate recommendations
            engine.generateRecommendations(userData);

            System.out.println("Recommendations generated successfully");

        } catch (IOException e) {
            System.out.println("Error processing files: " + e.getMessage());
        }
    }
}