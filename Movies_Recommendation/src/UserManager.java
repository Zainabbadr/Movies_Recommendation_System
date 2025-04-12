import java.util.*;
import java.io.*;

public class UserManager {
    private Set<String> seenUserIds = new HashSet<>();

    public boolean validateUsers(List<String> userData) {
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
        if (!userName.matches("[A-Za-z]+( [A-Za-z]+)*")) {
            FileHandler.writeFile("recommendations.txt", "ERROR: User Name " + userName + " is wrong");
            return false;
        }
        return true;
    }

    public boolean validateUserId(String userId) {
        if (!userId.matches("\\d{8}[A-Za-z]?") || userId.length() != 9 || seenUserIds.contains(userId)) {
            FileHandler.writeFile("recommendations.txt", "ERROR: User Id " + userId + " is wrong");
            return false;
        }
        return true;
    }
}
