import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class FileHandlerTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
private FileHandler fileHandler;
    private File testFile;
    private String testFilePath;

    @Before
    public void setUp() throws IOException {
        // Create a temporary file for testing
        testFile = tempFolder.newFile("test.txt");
        testFilePath = testFile.getAbsolutePath();
        fileHandler=new FileHandler();

        // Initialize with some content
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("Line 1\n");
            writer.write("Line 2\n");
            writer.write("  Line 3 with spaces  \n");
        }
    }

    @After
    public void tearDown() {
        // Ensure the file is deleted after each test
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testReadFile_Success() throws IOException {
        // Step 1: Call the method
        List<String> lines = fileHandler.readFile(testFilePath);

        // Step 2: Verify the results
        assertEquals("Should read 3 lines", 3, lines.size());
        assertEquals("Line 1", lines.get(0));
        assertEquals("Line 2", lines.get(1));
        assertEquals("Line 3 with spaces", lines.get(2)); // Trimmed as expected
    }

    @Test(expected = IOException.class)
    public void testReadFile_FileNotFound() throws IOException {
        // Step 1: Attempt to read a non-existent file
        fileHandler.readFile("non_existent_file.txt");
        // Step 2: The test should throw IOException
    }

    @Test
    public void testReadFile_EmptyFile() throws IOException {
        // Step 1: Create an empty file
        File emptyFile = tempFolder.newFile("empty.txt");

        // Step 2: Read the empty file
        List<String> lines = fileHandler.readFile(emptyFile.getAbsolutePath());

        // Step 3: Verify the result
        assertTrue("Should return an empty list for empty file", lines.isEmpty());
    }

    @Test
    public void testWriteFile_Success() throws IOException {
        // Step 1: Write to the file
        String message = "Test message";
        fileHandler.writeFile(testFilePath, message);

        // Step 2: Read the file content to verify
        String content = new String(Files.readAllBytes(Paths.get(testFilePath)));

        // Step 3: Verify the result
        assertEquals("Test message\n", content);
    }

    @Test
    public void testWriteFile_AppendMultipleMessages() throws IOException {
        // Step 1: Write multiple messages
        fileHandler.writeFile(testFilePath, "Message 1");
        fileHandler.writeFile(testFilePath, "Message 2");

        // Step 2: Read the file content
        String content = new String(Files.readAllBytes(Paths.get(testFilePath)));

        // Step 3: Verify that only the last message is in the file (overwrites by default)
        assertEquals("Message 2\n", content);
    }

    @Test
    public void testWriteFile_DirectoryDoesNotExist() {
        // Step 1: Attempt to write to a file in a non-existent directory
        String nonExistentPath = tempFolder.getRoot().getAbsolutePath() + "/non_existent_dir/file.txt";

        // Step 2: Write to the file (should catch IOException internally)
        fileHandler.writeFile(nonExistentPath, "Test message");

        // Step 3: Verify the file doesn't exist
        assertFalse("File should not be created in non-existent directory",
                new File(nonExistentPath).exists());
    }

    @Test
    public void testGetBufferedWriter_Success() throws IOException {
        // Step 1: Get a BufferedWriter
        BufferedWriter writer = fileHandler.getBufferedWriter(testFilePath);

        // Step 2: Use the writer
        writer.write("New content");
        writer.close();

        // Step 3: Verify the content was written
        String content = new String(Files.readAllBytes(Paths.get(testFilePath)));
        assertEquals("New content", content);
    }

    @Test(expected = IOException.class)
    public void testGetBufferedWriter_InvalidPath() throws IOException {
        // Step 1: Attempt to get a BufferedWriter for an invalid path
        String invalidPath = tempFolder.getRoot().getAbsolutePath() + "/non_existent_dir/file.txt";

        // Step 2: This should throw IOException
        fileHandler.getBufferedWriter(invalidPath);
    }

    @Test
    public void testWriteFile_EmptyMessage() throws IOException {
        // Step 1: Write an empty message
        fileHandler.writeFile(testFilePath, "");

        // Step 2: Read the content
        String content = new String(Files.readAllBytes(Paths.get(testFilePath)));

        // Step 3: Verify the result (just a newline)
        assertEquals("\n", content);
    }

    @Test
    public void testReadFile_WithSpecialCharacters() throws IOException {
        // Step 1: Write content with special characters
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("Line with special chars: !@#$%^&*()\n");
            writer.write("Unicode: 你好世界\n");
        }

        // Step 2: Read the file
        List<String> lines = fileHandler.readFile(testFilePath);

        // Step 3: Verify the results
        assertEquals(2, lines.size());
        assertEquals("Line with special chars: !@#$%^&*()", lines.get(0));
        assertEquals("Unicode: 你好世界", lines.get(1));
    }

    @Test
    public void testReadFile_LargeFile() throws IOException {
        // Step 1: Create a file with many lines
        File largeFile = tempFolder.newFile("large.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(largeFile))) {
            for (int i = 0; i < 1000; i++) {
                writer.write("Line " + i + "\n");
            }
        }

        // Step 2: Read the large file
        List<String> lines = fileHandler.readFile(largeFile.getAbsolutePath());

        // Step 3: Verify the results
        assertEquals(1000, lines.size());
        assertEquals("Line 0", lines.get(0));
        assertEquals("Line 999", lines.get(999));
    }
}