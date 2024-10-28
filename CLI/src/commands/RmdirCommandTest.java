package commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class RmdirCommandTest {
    private final String testDirName = "testDir";
    private final String nonExistentDirName = "nonExistentDir";
    @BeforeEach
    public void setUp() {
        new MkdirCommand().execute(new String[]{testDirName});
    }
    @Test
    public void testRemoveDirectory() {
        RmdirCommand rmdirCommand = new RmdirCommand();
        String[] args = {testDirName};
        rmdirCommand.execute(args);
        File testDir = new File(testDirName);
        assertFalse(testDir.exists(), "The directory should be deleted.");
    }

    @Test
    public void testRemoveNonExistentDirectory() {
        RmdirCommand rmdirCommand = new RmdirCommand();
        String[] args = {nonExistentDirName};
        rmdirCommand.execute(args);
        File nonExistentDir = new File(nonExistentDirName);
        assertFalse(nonExistentDir.exists(), "Non-existent directory should not exist.");
    }
    @AfterEach
    public void tearDown() {
        File testDir = new File(testDirName);
        if (testDir.exists()) {
            testDir.delete();
        }
        File nonExistentDir = new File(nonExistentDirName);
        if (nonExistentDir.exists()) {
            nonExistentDir.delete();
        }
    }
}
