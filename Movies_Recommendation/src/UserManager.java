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

            if (!name.matches("[A-Za-z]+( [A-Za-z]+)*")) {
                FileHandler.writeFile("recommendations.txt", "ERROR: User Name " + name + " is wrong");
                return false;
            }

            if (!id.matches("\\d{8}[A-Za-z]?") || id.length() != 9 || seenUserIds.contains(id)) {
                FileHandler.writeFile("recommendations.txt", "ERROR: User Id " + id + " is wrong");
                return false;
            }

            seenUserIds.add(id);
        }
        return true;
    }
}
