package commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class MkdirCommandTest {
    private final String testDirName = "testDir";
    private final String testParentDirPath = "parentDir";
    private File testParentDir;
    @BeforeEach
    public void setUp() {
        testParentDir = new File(testParentDirPath);
        if (!testParentDir.exists()) {
            testParentDir.mkdir();
        }
    }
    @Test
    public void testCreateDirectory() {
        MkdirCommand mkdirCommand = new MkdirCommand();
        String[] args = {testDirName};
        mkdirCommand.execute(testParentDir, args);

        File testDir = new File(testParentDir, testDirName);
        assertTrue(testDir.exists() && testDir.isDirectory(), "The directory should be created within the parent directory.");
    }
    @Test
    public void testCreateExistingDirectory() {
        File testDir = new File(testParentDir, testDirName);
        testDir.mkdir();
        MkdirCommand mkdirCommand = new MkdirCommand();
        String[] args = {testDirName};
        mkdirCommand.execute(testParentDir, args);
        assertTrue(testDir.exists() && testDir.isDirectory(), "The existing directory should still exist.");
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
