package commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
class LsACommandTest {
    private Path tempDir;
    private Path file1;
    private Path file2;
    private Path hiddenFile;
    private LsACommand lsACommand;

    @BeforeEach
    void setUp() throws IOException {
        // Set up a temporary directory with test files
        tempDir = Files.createTempDirectory("testDir");
        file1 = Files.createFile(tempDir.resolve("file1.txt"));
        file2 = Files.createFile(tempDir.resolve("file2.txt"));
        hiddenFile = Files.createFile(tempDir.resolve(".hiddenFile.txt"));
        lsACommand = new LsACommand();
    }
    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(file1);
        Files.deleteIfExists(file2);
        Files.deleteIfExists(hiddenFile);
        Files.deleteIfExists(tempDir);
    }
    @Test
    void testExecuteWithConsoleOutput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        lsACommand.execute(tempDir.toFile(), new String[]{});
        String output = outputStream.toString();
        System.setOut(System.out);
        assertTrue(output.contains("file1.txt"), "Output should contain file1.txt");
        assertTrue(output.contains("file2.txt"), "Output should contain file2.txt");
        assertTrue(output.contains(".hiddenFile.txt"), "Output should contain .hiddenFile.txt (hidden file)");
    }
    @Test
    void testEmptyDirectory() throws IOException {
        Path emptyDir = Files.createTempDirectory("emptyDir");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        lsACommand.execute(emptyDir.toFile(), outputStream);
        String output = outputStream.toString();
        assertTrue(output.isEmpty(), "Output should be empty for an empty directory");
        Files.deleteIfExists(emptyDir);
    }
}
