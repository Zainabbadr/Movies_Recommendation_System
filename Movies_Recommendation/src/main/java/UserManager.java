import java.util.*;

public class UserManager {
    private Set<String> userIds = new HashSet<>();

    public boolean validateUsers(List<String> userData) {
        for (int i = 0; i < userData.size(); i += 2) {
            String[] userInfo = userData.get(i).split(",");
            String name = userInfo[0];
            String id = userInfo[1];

            if (!validateUserName(name) || !validateUserId(id)) {
                return false;
            }
            userIds.add(id);
        }
        return true;
    }

    private boolean validateUserName(String name) {
        return name.matches("[A-Za-z]+( [A-Za-z]+)*");
    }

    private boolean validateUserId(String userId) {
        return userId.matches("\\d{8}[A-Za-z]?") && userId.length() == 9 && !userIds.contains(userId);
    }
}
