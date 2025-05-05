import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileHandler fileHandler=new FileHandler();
        UserManager userManager=new UserManager(fileHandler,"users.txt");
        MovieManager movieManager=new MovieManager(fileHandler,"movies.txt");
        movieManager.readMovies();
        userManager.readUsers();
        RecommendationManager recommendationEngine=new RecommendationManager(movieManager,userManager);
        List<String> output= recommendationEngine.recommend();
        String concatenatedOutput = String.join("\n", output);
        fileHandler.writeFile("recommendations.txt", concatenatedOutput);
    }
}
