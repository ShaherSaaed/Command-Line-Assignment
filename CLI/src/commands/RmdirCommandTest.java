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
    private final String testParentDirPath = "parentDir";
    private File testParentDir;

    @BeforeEach
    public void setUp() {
        testParentDir = new File(testParentDirPath);
        if (!testParentDir.exists()) {
            testParentDir.mkdir();
        }
        new MkdirCommand().execute(testParentDir, new String[]{testDirName});
    }

    @Test
    public void testRemoveDirectory() {
        RmdirCommand rmdirCommand = new RmdirCommand();
        String[] args = {testDirName};
        rmdirCommand.execute(testParentDir, args);

        File testDir = new File(testParentDir, testDirName);
        assertFalse(testDir.exists(), "The directory should be deleted.");
    }

    @Test
    public void testRemoveNonExistentDirectory() {
        RmdirCommand rmdirCommand = new RmdirCommand();
        String[] args = {nonExistentDirName};
        rmdirCommand.execute(testParentDir, args);

        File nonExistentDir = new File(testParentDir, nonExistentDirName);
        assertFalse(nonExistentDir.exists(), "Non-existent directory should not exist.");
    }

    @AfterEach
    public void tearDown() {
        File testDir = new File(testParentDir, testDirName);
        if (testDir.exists()) {
            testDir.delete();
        }
        if (testParentDir.exists()) {
            testParentDir.delete();
        }
    }
}
