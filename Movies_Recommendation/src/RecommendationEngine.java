import java.util.*;
import java.io.*;

public class RecommendationEngine {
    private static final String MOVIE_FILE = "movies.txt";
    private static final String USER_FILE = "users.txt";
    private static final String OUTPUT_FILE = "recommendations.txt";

    public static void main(String[] args) {
        try {
            List<String> movieData = FileHandler.readFile(MOVIE_FILE);
            List<String> userData = FileHandler.readFile(USER_FILE);

            MovieManager movieManager = new MovieManager();
            UserManager userManager = new UserManager();

            if (!movieManager.loadMovies(movieData)) {
                FileHandler.writeFile(OUTPUT_FILE, "ERROR: Invalid movie data");
                return;
            }

            if (!userManager.validateUsers(userData)) {
                FileHandler.writeFile(OUTPUT_FILE, "ERROR: Invalid user data");
                return;
            }

            try (BufferedWriter writer = FileHandler.getBufferedWriter(OUTPUT_FILE)) {
                for (int i = 0; i < userData.size(); i += 2) {
                    String[] userInfo = userData.get(i).split(",");
                    String[] likedMovies = userData.get(i + 1).split(",");

                    Set<String> recommended = new LinkedHashSet<>();
                    for (String movieId : likedMovies) {
                        for (String genre : movieManager.getGenres(movieId)) {
                            recommended.addAll(movieManager.getMoviesByGenre(genre));
                        }
                    }

                    recommended.removeAll(Arrays.asList(likedMovies));
                    writer.write(userInfo[0] + "," + userInfo[1] + "\n");

                    if (recommended.isEmpty()) {
                        writer.write("No Recommendations\n");
                    } else {
                        String recommendations = recommended.stream()
                                .map(movieManager::getTitle)
                                .filter(Objects::nonNull)
                                .reduce((a, b) -> a + "," + b)
                                .orElse("No Recommendations");
                        writer.write(recommendations + "\n");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
