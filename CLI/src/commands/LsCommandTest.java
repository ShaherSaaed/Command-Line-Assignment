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

class LsCommandTest {
    private Path tempDir;
    private Path file1;
    private Path file2;
    private LsCommand lsCommand;
    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("testDir");
        file1 = Files.createFile(tempDir.resolve("file1.txt"));
        file2 = Files.createFile(tempDir.resolve("file2.txt"));
        lsCommand = new LsCommand();
    }
    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(file1);
        Files.deleteIfExists(file2);
        Files.deleteIfExists(tempDir);
    }
    @Test
    void testExecuteWithConsoleOutput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));
        lsCommand.execute(tempDir.toFile(), new String[]{});
        String output = outputStream.toString();
        System.setOut(System.out);
        assertTrue(output.contains("file1.txt"), "Output should contain file1.txt");
        assertTrue(output.contains("file2.txt"), "Output should contain file2.txt");
    }
    @Test
    void testExecuteWithOutputStream() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        lsCommand.execute(tempDir.toFile(), outputStream);
        String output = outputStream.toString();
        assertTrue(output.contains("file1.txt"), "Output should contain file1.txt");
        assertTrue(output.contains("file2.txt"), "Output should contain file2.txt");
    }
    @Test
    void testEmptyDirectory() throws IOException {
        Path emptyDir = Files.createTempDirectory("emptyDir");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        lsCommand.execute(emptyDir.toFile(), outputStream);
        String output = outputStream.toString();
        assertTrue(output.isEmpty(), "Output should be empty for an empty directory");
        Files.deleteIfExists(emptyDir);
    }
}
