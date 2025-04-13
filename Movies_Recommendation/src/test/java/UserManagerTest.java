import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserManagerTest {
    private UserManager userManager;
    private String userName;
    private String id;

    @Before
    public void setUp() throws Exception {
        userManager = new UserManager();
    }

    @Test
    // Testcase 2
    public void validName_twoWords(){
        userName = "Correct Username";
        assertTrue("Return value should be True", userManager.validateUserTitle(userName));
    }

    // Testcase 3
    @Test
    public void invalidName_startWithSpace() {
        userName = " Space Username";
        assertFalse("Return value should be False", userManager.validateUserTitle(userName));
    }

    // Testcase 4
    @Test
    public void invalidName_specialChar(){
        userName = "Wrong$username";
        assertFalse("Return value should be False", userManager.validateUserTitle(userName));
    }

    // Testcase 5
    @Test
    public void invalidName_number(){
        userName = "Wrong7username";
        assertFalse("Return value should be False", userManager.validateUserTitle(userName));
    }

    // Testcase 6
    @Test
    public void invalidName_combined(){
        userName = " Wrong7Username@";
        assertFalse("Return value should be False", userManager.validateUserTitle(userName));
    }

    // Testcase 7
    @Test
    public void validId_lowercaseLetter(){
        id = "12345678a";
        assertTrue("Return value should be True", userManager.validateUserId(id));
    }

    // Testcase 8
    @Test
    public void validId_uppercaseLetter(){
        id = "12345678A";
        assertTrue("Return value should be True", userManager.validateUserId(id));
    }

    // Testcase 9
    @Test
    public void validId_allNumbers(){
        id = "123456789";
        assertTrue("Return value should be True", userManager.validateUserId(id));
    }

    // Testcase 10
    @Test
    public void invalidId_notUnique(){

    }

    // Testcase 11
    @Test
    public void invalidId_moreThan9(){
        id = "1234567899";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    // Testcase 12
    @Test
    public void invalidId_specialChar(){
        id = "12345678&";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    // Testcase 13
    @Test
    public void invalidId_combined(){
        id = "123456789$";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    // Testcase 14
    @Test
    public void invalidId_8nums(){
        id = "12345678";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    // Testcase 15
    @Test
    public void invalidId_6nums(){
        id = "123456";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    // Testcase 16
    @Test
    public void invalidId_empty(){
        id = "";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    // Testcase 17
    @Test
    public void invalidId_letterAtStart(){
        id = "J12345678";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    // Testcase 18
    @Test
    public void invalidId_letterAtRandom(){
        id = "123K45678";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    // Testcase 19
    @Test
    public void invalidId_letterAtStartAndEnd(){
        id = "A1234567k";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }

    // Testcase 20
    @Test
    public void invalidId_letterAtRandomAndEnd(){
        id = "123G4567K";
        assertFalse("Return value should be False", userManager.validateUserId(id));
    }
}