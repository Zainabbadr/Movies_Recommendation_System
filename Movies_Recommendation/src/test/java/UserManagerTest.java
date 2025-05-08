import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserManagerTest {
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
        assertTrue("Method should return true for a valid single-word username", userManager.validateUserTitle(userName));
    }

    @Test
    public void validName_twoWords() {
        userName = "Correct Username";
        assertTrue("Method should return true for a valid two-word username", userManager.validateUserTitle(userName));
    }

    @Test
    public void validName_multipleWords() {
        userName = "John Doe Smith";
        assertTrue("Method should return true for a valid multi-word username", userManager.validateUserTitle(userName));
    }

    @Test
    public void invalidName_startWithSpace() {
        userName = " Space Username";
        assertFalse("Method should return false for an invalid username starting with space", userManager.validateUserTitle(userName));
    }

    @Test
    public void invalidName_specialChar() {
        userName = "Wrong$username";
        assertFalse("Method should return false for an invalid username with special characters", userManager.validateUserTitle(userName));
    }

    @Test
    public void invalidName_number() {
        userName = "Wrong7username";
        assertFalse("Method should return false for an invalid username with numbers", userManager.validateUserTitle(userName));
    }

    @Test
    public void invalidName_combined() {
        userName = " Wrong7Username@";
        assertFalse("Method should return false for an invalid username with multiple problems", userManager.validateUserTitle(userName));
    }

    @Test
    public void invalidName_empty() {
        userName = "";
        assertFalse("Method should return false for an empty username", userManager.validateUserTitle(userName));
    }

    @Test
    public void validId_lowercaseLetter() {
        id = "12345678a";
        assertTrue("Method should return true for a valid ID format", userManager.validateUserId(id));
    }

    @Test
    public void validId_uppercaseLetter() {
        id = "12345678A";
        assertTrue("Method should return true for a valid ID format", userManager.validateUserId(id));
    }

    @Test
    public void validId_allNumbers() {
        id = "123456789";
        assertTrue("Method should return true for a valid ID format", userManager.validateUserId(id));
    }


    @Test
    public void invalidId_moreThan9() {
        id = "1234567899";
        assertFalse("Method should return false for an invalid ID length", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_specialChar() {
        id = "12345678&";
        assertFalse("Method should return false for an invalid ID with special characters", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_combined() {
        id = "123456789$";
        assertFalse("Method should return false for an invalid ID with multiple problems", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_8nums() {
        id = "12345678";
        assertFalse("Method should return false for an invalid ID with insufficient length", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_6nums() {
        id = "123456";
        assertFalse("Method should return false for an invalid ID with insufficient length", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_empty() {
        id = "";
        assertFalse("Method should return false for an empty ID", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_letterAtStart() {
        id = "J12345678";
        assertFalse("Method should return false for an invalid ID format", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_letterAtRandom() {
        id = "123K45678";
        assertFalse("Method should return false for an invalid ID format", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_letterAtStartAndEnd() {
        id = "A1234567k";
        assertFalse("Method should return false for an invalid ID format", userManager.validateUserId(id));
    }

    @Test
    public void invalidId_letterAtRandomAndEnd() {
        id = "123G4567K";
        assertFalse("Method should return false for an invalid ID format", userManager.validateUserId(id));
    }

    @Test
    public void validateUsers_validData() {
        List<String> userData = new ArrayList<>();
        userData.add("John Doe,123456789");
        userData.add("Jane Smith,987654321");
        userManager.userData=userData;
        assertTrue("Method should return true for valid user data", userManager.validateUsers());
    }

    @Test
    public void validateUsers_invalidName() {
        List<String> userData = new ArrayList<>();
        userData.add("John123,123456789");
        userManager.userData=userData;
        assertFalse("Should fail validation due to invalid name", userManager.validateUsers());
    }

    @Test
    public void validateUsers_invalidId() {
        List<String> userData = new ArrayList<>();
        userData.add("John Doe,12345");
        userManager.userData=userData;
        assertFalse("Should fail validation due to invalid ID", userManager.validateUsers());
    }

    @Test
    public void validateUsers_duplicateId() {
        List<String> userData = new ArrayList<>();
        userData.add("John Doe,123456789");
        userData.add("a");
        userData.add("Jane Smith,123456789"); // Duplicate ID
        userData.add("a");
        userManager.userData=userData;
        assertFalse("Should fail validation due to duplicate ID", userManager.validateUsers());
    }

    @Test
    public void validateUsers_emptyList() {
        List<String> userData = new ArrayList<>();
        userManager.userData=userData;
        assertTrue("Empty list should validate successfully", userManager.validateUsers());
    }

    @Test
    public void validateUsers_invalidFormat() {
        List<String> userData = new ArrayList<>();
        userData.add("Invalid Format");
        userData.add("movie data");
        userManager.userData=userData;
        assertTrue("Should handle invalid format gracefully", userManager.validateUsers());
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