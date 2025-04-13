import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserManagerTest2 {
    private UserManager userManager;
    private String userName;
    private String id;

    @Before
    public void setUp() {
        userManager = new UserManager();
    }

    // Missing testcase 1 - single word name
    @Test
    public void validName_singleWord() {
        userName = "Username";
        assertTrue("Return value should be True", userManager.validateUserTitle(userName));
    }

    @Test
    public void validName_twoWords() {
        userName = "Correct Username";
        assertTrue("Return value should be True", userManager.validateUserTitle(userName));
    }

    // Add test for multiple words
    @Test
    public void validName_multipleWords() {
        userName = "John Doe Smith";
        assertTrue("Return value should be True", userManager.validateUserTitle(userName));
    }

    @Test
    public void invalidName_startWithSpace() {
        userName = " Space Username";
        assertFalse("Return value should be False", userManager.validateUserTitle(userName));
    }

    @Test
    public void invalidName_specialChar() {
        userName = "Wrong$username";
        assertFalse("Return value should be False", userManager.validateUserTitle(userName));
    }

    @Test
    public void invalidName_number() {
        userName = "Wrong7username";
        assertFalse("Return value should be False", userManager.validateUserTitle(userName));
    }

    @Test
    public void invalidName_combined() {
        userName = " Wrong7Username@";
        assertFalse("Return value should be False", userManager.validateUserTitle(userName));
    }

    // Add test for empty name
    @Test
    public void invalidName_empty() {
        userName = "";
        assertFalse("Return value should be False", userManager.validateUserTitle(userName));
    }

    @Test
    public void validId_lowercaseLetter() {
        id = "12345678a";
        assertTrue("Return value should be True", userManager.validateUserId(id));
    }

    @Test
    public void validId_uppercaseLetter() {
        id = "12345678A";
        assertTrue("Return value should be True", userManager.validateUserId(id));
    }

    @Test
    public void validId_allNumbers() {
        id = "123456789";
        assertTrue("Return value should be True", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_notUnique() {
        // First add a valid ID
        id = "123456789";
        assertTrue("First validation should succeed", userManager.validateUserId(id));

        // Then try to add the same ID again
        assertFalse("Duplicate ID should fail validation", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_moreThan9() {
        id = "1234567899";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_specialChar() {
        id = "12345678&";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_combined() {
        id = "123456789$";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_8nums() {
        id = "12345678";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_6nums() {
        id = "123456";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_empty() {
        id = "";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_letterAtStart() {
        id = "J12345678";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_letterAtRandom() {
        id = "123K45678";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_letterAtStartAndEnd() {
        id = "A1234567k";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_letterAtRandomAndEnd() {
        id = "123G4567K";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }
    
    @Test
    public void validateUsers_validData() {
        List<String> userData = new ArrayList<>();
        userData.add("John Doe,123456789");
//        userData.add("movie data"); // Simulating movie data
        userData.add("Jane Smith,987654321");
//        userData.add("movie data"); // Simulating movie data

        assertTrue("Should validate successfully", userManager.validateUsers(userData));
    }

    @Test
    public void validateUsers_invalidName() {
        List<String> userData = new ArrayList<>();
        userData.add("John123,123456789");

        assertFalse("Should fail validation due to invalid name", userManager.validateUsers(userData));
    }

    @Test
    public void validateUsers_invalidId() {
        List<String> userData = new ArrayList<>();
        userData.add("John Doe,12345");

        assertFalse("Should fail validation due to invalid ID", userManager.validateUsers(userData));
    }

    @Test
    public void validateUsers_duplicateId() {
        List<String> userData = new ArrayList<>();
        userData.add("John Doe,123456789");
        userData.add("Jane Smith,123456789"); // Duplicate ID

        assertFalse("Should fail validation due to duplicate ID", userManager.validateUsers(userData));
    }

    @Test
    public void validateUsers_emptyList() {
        List<String> userData = new ArrayList<>();

        assertTrue("Empty list should validate successfully", userManager.validateUsers(userData));
    }

    @Test
    public void validateUsers_invalidFormat() {
        List<String> userData = new ArrayList<>();
        userData.add("Invalid Format");
        userData.add("movie data");

        assertTrue("Should handle invalid format gracefully", userManager.validateUsers(userData));
    }

    // Test with mocking FileHandler to verify error messages
    @Test
    public void testFileHandlerCalled_invalidName() {
        try (MockedStatic<FileHandler> mockedFileHandler = Mockito.mockStatic(FileHandler.class)) {
            userName = "Invalid$Name";
            userManager.validateUserTitle(userName);

            mockedFileHandler.verify(() ->
                    FileHandler.writeFile(eq("recommendations.txt"), contains("ERROR: User Name Invalid$Name is wrong")));
        }
    }

    @Test
    public void testFileHandlerCalled_invalidId() {
        try (MockedStatic<FileHandler> mockedFileHandler = Mockito.mockStatic(FileHandler.class)) {
            id = "12345";
            userManager.validateUserId(id);

            mockedFileHandler.verify(() ->
                    FileHandler.writeFile(eq("recommendations.txt"), contains("ERROR: User Id 12345 is wrong")));
        }
    }
}