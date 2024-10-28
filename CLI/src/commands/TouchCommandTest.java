package commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class TouchCommandTest {
    private final String testFileName = "testFile.txt";
    @Test
    public void testCreateFile() {
        TouchCommand touchCommand = new TouchCommand();
        String[] args = {testFileName};
        touchCommand.execute(args);
        File testFile = new File(testFileName);
        assertTrue(testFile.exists() && testFile.isFile(), "The file should be created.");
    }
    @Test
    public void testCreateExistingFile() {
        TouchCommand touchCommand = new TouchCommand();
        String[] args = {testFileName};
        touchCommand.execute(args);
        File testFile = new File(testFileName);
        assertTrue(testFile.exists() && testFile.isFile(), "The file should exist after the first creation.");
        touchCommand.execute(args);
        assertTrue(testFile.exists() && testFile.isFile(), "The existing file should still exist and be a file.");
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(testFileName);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
