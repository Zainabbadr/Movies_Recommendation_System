import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> output=null;
        FileHandler fileHandler=new FileHandler();
        UserManager userManager=new UserManager(fileHandler,"users.txt");
        MovieManager movieManager=new MovieManager(fileHandler,"movies.txt");
        movieManager.readMovies();
        userManager.readUsers();
        RecommendationManager recommendationEngine=new RecommendationManager(movieManager,userManager);
         output= recommendationEngine.recommend();
        if (output!=null){
        String concatenatedOutput = String.join("\n", output);
        fileHandler.writeFile("recommendations.txt", concatenatedOutput);
    }}
}
