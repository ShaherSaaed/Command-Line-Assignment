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
class LsRCommandTest {
    private Path tempDir;
    private Path file1;
    private Path file2;
    private LsRCommand lsRCommand;
    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("testDir");
        file1 = Files.createFile(tempDir.resolve("file1.txt"));
        file2 = Files.createFile(tempDir.resolve("file2.txt"));
        lsRCommand = new LsRCommand();
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
        lsRCommand.execute(tempDir.toFile(), new String[]{});
        String output = outputStream.toString();
        System.setOut(System.out);
        assertTrue(output.contains("file2.txt"));
        assertTrue(output.contains("file1.txt"));
        assertTrue(output.indexOf("file2.txt") < output.indexOf("file1.txt"),
                "file2.txt should appear before file1.txt in reversed order");
    }

    @Test
    void testExecuteWithOutputStream() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        lsRCommand.execute(tempDir.toFile(), outputStream);
        String output = outputStream.toString();
        assertTrue(output.contains("file2.txt"));
        assertTrue(output.contains("file1.txt"));
        assertTrue(output.indexOf("file2.txt") < output.indexOf("file1.txt"),
                "file2.txt should appear before file1.txt in reversed order");
    }
}
