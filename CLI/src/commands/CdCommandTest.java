package commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
public class CdCommandTest {
    private CdCommand cdCommand;
    private File initialDirectory;
    @BeforeEach
    public void setUp() {
        cdCommand = new CdCommand();
        initialDirectory = cdCommand.getCurrentDirectory();
    }
    @AfterEach
    public void tearDown() {
        cdCommand.execute(new String[]{initialDirectory.getAbsolutePath()});
    }
    @Test
    public void testChangeToSpecificDirectory() {
        File newDir = new File(initialDirectory, "src");
        if (newDir.exists() && newDir.isDirectory()) {
            cdCommand.execute(new String[]{"src"});
            assertEquals(newDir.getAbsolutePath(), cdCommand.getCurrentDirectory().getAbsolutePath());
        } else {
            System.out.println("Directory 'src' does not exist, skipping test.");
        }
    }
    @Test
    public void testChangeToParentDirectory() {
        File parentDir = initialDirectory.getParentFile();
        cdCommand.execute(new String[]{".."});
        assertEquals(parentDir.getAbsolutePath(), cdCommand.getCurrentDirectory().getAbsolutePath());
    }
    @Test
    public void testInvalidDirectory() {
        cdCommand.execute(new String[]{"nonexistent_directory"});
        assertEquals(initialDirectory.getAbsolutePath(), cdCommand.getCurrentDirectory().getAbsolutePath());
    }
    @Test
    public void testNoArgumentsPrintsCurrentDirectory() {
        File currentDir = cdCommand.getCurrentDirectory();
        cdCommand.execute(new String[]{});
        assertEquals(currentDir.getAbsolutePath(), cdCommand.getCurrentDirectory().getAbsolutePath());
    }
}