import java.util.*;

public class RecommendationManager {

    private static final String OUTPUT_FILE = "recommendations.txt";
    MovieManager movieManager ;
    UserManager userManager ;
    List<String> output=new ArrayList<>() ;

public  RecommendationManager(MovieManager movieManager , UserManager userManager ){
        this.movieManager=movieManager;
        this.userManager=userManager;
}
    public boolean loadMovies(List<String> movieData) {
    movieManager.movieData=movieData;
        return movieManager.loadMovies();
    }

    public boolean validateUsers(List<String> userData) {
    userManager.userData=userData;
        return userManager.validateUsers();
    }
    public List<String> recommend() {


            if (!movieManager.loadMovies()) return null;
            if (!userManager.validateUsers()) return null;

                for (int i = 0; i < userManager.userData.size(); i += 2) {
                    String[] userInfo = userManager.userData.get(i).split(",");
                    String[] likedMovies = userManager.userData.get(i + 1).split(",");

                    Set<String> recommended = new LinkedHashSet<>();
                    for (String movieId : likedMovies) {
                        for (String genre : movieManager.getGenres(movieId)) {
                            recommended.addAll(movieManager.getMoviesByGenre(genre));
                        }
                    }

                    recommended.removeAll(Arrays.asList(likedMovies));
                    output.add(userInfo[0] + "," + userInfo[1] );

                    if (recommended.isEmpty()) {
                        output.add("No Recommendations");
                    } else {
                        String recommendations = recommended.stream()
                                .map(movieManager::getTitle)
                                .filter(Objects::nonNull)
                                .reduce((a, b) -> a + "," + b)
                                .orElse("No Recommendations");
                        output.add(recommendations);
                    }
                }

                return output;
//
    }


}

