import java.util.*;
import java.io.*;

public class UserManager {
    // --- Error Message Constants ---
    public static final String ERROR_USER_NAME =
            "ERROR: User Name {user_name} is wrong";
    public static final String ERROR_USER_ID =
            "ERROR: User Id {user_id} is wrong";

    // --- Fields ---
    private Set<String> seenUserIds = new HashSet<>();
    List<String> userData;
    public String filename;
    FileHandler fileHandler;
    public   UserManager(FileHandler fileHandler,String filename){
        this.fileHandler=fileHandler;
        this.filename=filename;

    }
    public   UserManager(){}

    public void readUsers(){
        try {
            this.userData= fileHandler.readFile(filename);
        }catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    public boolean validateUsers() {
        for (int i = 0; i < userData.size(); i += 2) {
            String[] info = userData.get(i).split(",");
            if (info.length < 2) continue;

            String name = info[0];
            String id = info[1];

            if (!validateUserTitle(name)) return false;
            if (!validateUserId(id)) return false;

            seenUserIds.add(id);
        }
        return true;
    }

    public boolean validateUserTitle(String userName) {
        boolean ok = userName.matches("[A-Za-z]+( [A-Za-z]+)*");
        if (!ok) {
            FileHandler.writeFile("recommendations.txt",ERROR_USER_NAME.replace("{user_name}", userName));
        }
        return ok;
    }

    public boolean validateUserId(String userId) {
        boolean ok = userId.matches("(\\d{9}|\\d{8}[A-Za-z])$")
                && userId.length() == 9
                && !seenUserIds.contains(userId);
        if (!ok) {
            FileHandler.writeFile(
                    "recommendations.txt",
                    ERROR_USER_ID.replace("{user_id}", userId)
            );
        }
        return ok;
    }
}
