package commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RmCommandTest {
    private final String testFileName = "testFile.txt";
    private final String nonExistentFileName = "nonExist.txt";
    private final String testParentDirPath = "parentDir";
    private File testParentDir;

    @BeforeEach
    public void setUp() {
        testParentDir = new File(testParentDirPath);
        if (!testParentDir.exists()) {
            testParentDir.mkdir();
        }
        new TouchCommand().execute(testParentDir,new String[]{testFileName});
    }

    @Test
    public void testRemoveFile() {
        RmCommand rmCommand = new RmCommand();
        String[] args = {testFileName};
        rmCommand.execute(testParentDir, args);
        File testFile = new File(testParentDir, testFileName);
        assertFalse(testFile.exists(), "The file should be deleted.");
    }

    @Test
    public void testRemoveNonExistentFile() {
        RmCommand rmCommand = new RmCommand();
        String[] args = {nonExistentFileName};
        rmCommand.execute(testParentDir, args);

        File nonExistentFile = new File(testParentDir, nonExistentFileName);
        assertFalse(nonExistentFile.exists(), "Non-existent file should not exist.");
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(testParentDir, testFileName);
        if (testFile.exists()) {
            testFile.delete();
        }
        File nonExistentFile = new File(testParentDir, nonExistentFileName);
        if (nonExistentFile.exists()) {
            nonExistentFile.delete();
        }
        if (testParentDir.exists()) {
            testParentDir.delete();
        }
    }
}
