import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConditionCoverage {

    @Test
    void testValidUserNameAndId() {
        UserManager um = new UserManager();
        assertTrue(um.validateUserTitle("Alice Smith"));
        assertTrue(um.validateUserId("12345678A"));
    }

    @Test
    void testInvalidUserName() {
        UserManager um = new UserManager();
        String name = "123Alice";
        assertFalse(um.validateUserTitle(name));
        String expected = UserManager.ERROR_USER_NAME.replace("{user_name}", name);
        // assertEquals(expected, getFirstLineOfFile("recommendations.txt"));
    }

    @Test
    void testInvalidUserId() {
        UserManager um = new UserManager();
        String id = "12A";
        assertFalse(um.validateUserId(id));
        String expected = UserManager.ERROR_USER_ID.replace("{user_id}", id);
        // assertEquals(expected, getFirstLineOfFile("recommendations.txt"));
    }
}
