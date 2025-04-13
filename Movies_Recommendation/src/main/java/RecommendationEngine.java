import java.util.*;
import java.io.*;

import java.util.*;
import java.io.*;

public class RecommendationEngine {
    private static final String OUTPUT_FILE = "recommendations.txt";

    private final MovieManager movieManager;
    private final UserManager userManager;

    public RecommendationEngine() {
        this.movieManager = new MovieManager();
        this.userManager = new UserManager();
    }

    public boolean loadMovies(List<String> movieData) {
        return movieManager.loadMovies(movieData);
    }

    public boolean validateUsers(List<String> userData) {
        return userManager.validateUsers(userData);
    }

    public void generateRecommendations(List<String> userData) throws IOException {
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
    }
}
