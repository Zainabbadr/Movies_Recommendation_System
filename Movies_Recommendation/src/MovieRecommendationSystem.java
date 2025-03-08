import java.io.*;
import java.util.*;
import java.util.regex.*;

public class MovieRecommendationSystem {
    private static final String MOVIE_FILE = "movies.txt";
    private static final String USER_FILE = "users.txt";
    private static final String OUTPUT_FILE = "recommendations.txt";

    public static void main(String[] args) {
        try {
            List<String> movieData = readFile(MOVIE_FILE);
            List<String> userData = readFile(USER_FILE);

            if (validateInputs(movieData, userData)) {
                generateRecommendations(movieData, userData);
            }
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    private static List<String> readFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    private static void generateRecommendations(List<String> movieData, List<String> userData) {
        Map<String, Set<String>> movieGenres = new HashMap<>();
        Map<String, String> movieTitles = new HashMap<>();
        Map<String, Set<String>> genreToMovies = new HashMap<>();

        // Build movie data maps
        for (int i = 0; i < movieData.size(); i += 2) {
            String[] movieInfo = movieData.get(i).split(",");
            String[] genres = movieData.get(i + 1).split(",");
            movieTitles.put(movieInfo[1], movieInfo[0]);
            for (String genre : genres) {
                movieGenres.computeIfAbsent(movieInfo[1], k -> new HashSet<>()).add(genre);
                genreToMovies.computeIfAbsent(genre, k -> new HashSet<>()).add(movieInfo[1]);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            // Process user data and generate recommendations
            for (int i = 0; i < userData.size(); i += 2) {
                String[] userInfo = userData.get(i).split(",");
                String[] likedMovies = userData.get(i + 1).split(",");
                Set<String> recommendedMovies = new LinkedHashSet<>();

                // Generate recommendations based on genres
                for (String movieId : likedMovies) {
                    Set<String> genres = movieGenres.get(movieId);
                    if (genres != null) {
                        for (String genre : genres) {
                            if (genreToMovies.containsKey(genre)) {
                                recommendedMovies.addAll(genreToMovies.get(genre));
                            }
                        }
                    }
                }

                // Remove liked movies from recommendations
                recommendedMovies.removeAll(Arrays.asList(likedMovies));

                // Write user info to the file (name and ID)
                writer.write(userInfo[0] + "," + userInfo[1] + "\n");

                // Write recommended movie titles or "No Recommendations"
                if (!recommendedMovies.isEmpty()) {
                    writer.write(recommendedMovies.stream()
                            .map(movieTitles::get)
                            .filter(Objects::nonNull)
                            .reduce((a, b) -> a + "," + b)
                            .orElse("") + "\n");
                } else {
                    writer.write("No Recommendations\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static boolean validateInputs(List<String> movieData, List<String> userData) {
        Set<String> userIds = new HashSet<>();
        for (int i = 0; i < userData.size(); i += 2) {
            String[] userInfo = userData.get(i).split(",");
            if (!validateUserName(userInfo[0])) {
                writeError("ERROR: User Name " + userInfo[0] + " is wrong");
                return false;
            }
            if (!validateUserId(userInfo[1], userIds)) {
                writeError("ERROR: User Id " + userInfo[1] + " is wrong");
                return false;
            }
            userIds.add(userInfo[1]);
        }
        for (int i = 0; i < movieData.size(); i += 2) {
            String[] movieInfo = movieData.get(i).split(",");
            if (!validateMovieTitle(movieInfo[0])) {
                writeError("ERROR: Movie Title " + movieInfo[0] + " is wrong");
                return false;
            }
            if (!validateMovieId(movieInfo[1], movieInfo[0])) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateUserName(String name) {
        return name.matches("[A-Za-z]+( [A-Za-z]+)*");
    }

    private static boolean validateUserId(String userId, Set<String> userIds) {
        return userId.matches("\\d{8}[A-Za-z]?") && userId.length() == 9 && !userIds.contains(userId);
    }

    private static boolean validateMovieTitle(String title) {
        return title.matches("([A-Z][a-z]*)( [A-Z][a-z]*)*");
    }

    private static boolean validateMovieId(String movieId, String title) {
        String expectedIdPrefix = title.replaceAll("[^A-Z]", "");
        String idPrefix = movieId.replaceAll("\\d", "");
        String idNumbers = movieId.replaceAll("\\D", "");

        // Check if ID prefix matches expected and numbers are unique
        if (!idPrefix.equals(expectedIdPrefix)) {
            writeError("ERROR: Movie Id letters " + movieId + " are wrong");
            return false;
        }
        if (new HashSet<>(Arrays.asList(idNumbers.split(""))).size() != idNumbers.length()) {
            writeError("ERROR: Movie Id numbers " + movieId + " aren’t unique");
            return false;
        }
        return true;
    }

    private static void writeError(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            writer.write(message);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
