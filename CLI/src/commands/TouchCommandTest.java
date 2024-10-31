package commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TouchCommandTest {
    private final String testFileName = "testFile.txt";
    private final String testDirectoryPath = "testDir";
    private File testDirectory;

    @BeforeEach
    public void setUp() {
        testDirectory = new File(testDirectoryPath);
        if (!testDirectory.exists()) {
            testDirectory.mkdir();
        }
    }

    @Test
    public void testCreateFile() {
        TouchCommand touchCommand = new TouchCommand();
        String[] args = {testFileName};
        touchCommand.execute(testDirectory, args);

        File testFile = new File(testDirectory, testFileName);
        assertTrue(testFile.exists() && testFile.isFile(), "The file should be created.");
    }

    @Test
    public void testCreateExistingFile() {
        TouchCommand touchCommand = new TouchCommand();
        String[] args = {testFileName};

        touchCommand.execute(testDirectory, args);
        File testFile = new File(testDirectory, testFileName);
        assertTrue(testFile.exists() && testFile.isFile(), "The file should exist after the first creation.");

        // Execute again to test behavior with an existing file
        touchCommand.execute(testDirectory, args);
        assertTrue(testFile.exists() && testFile.isFile(), "The existing file should still exist and be a file.");
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(testDirectory, testFileName);
        if (testFile.exists()) {
            testFile.delete();
        }
        if (testDirectory.exists()) {
            testDirectory.delete();
        }
    }
}
