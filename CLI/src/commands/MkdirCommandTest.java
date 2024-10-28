package commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class MkdirCommandTest {
    private final String testDirName = "testDir";
    @Test
    public void testCreateDirectory() {
        MkdirCommand mkdirCommand = new MkdirCommand();
        String[] args = {testDirName};
        mkdirCommand.execute(args);
        File testDir = new File(testDirName);
        assertTrue(testDir.exists() && testDir.isDirectory());
    }
    @Test
    public void testCreateExistingDirectory() {
        File testDir = new File(testDirName);
        testDir.mkdir();
        MkdirCommand mkdirCommand = new MkdirCommand();
        String[] args = {testDirName};
        mkdirCommand.execute(args);
        assertTrue(testDir.exists() && testDir.isDirectory());
    }
    @AfterEach
    public void tearDown() {
        File testDir = new File(testDirName);
        if (testDir.exists()) {
            testDir.delete();
        }
    }
}