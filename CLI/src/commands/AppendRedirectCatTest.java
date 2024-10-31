package commands;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class AppendRedirectCatTest {
    private AppendRedirectCatCommand command;
    private Path tempDirectory;
    private File file1;
    private File file2;

    @BeforeEach
    public void setUp() throws IOException {
        command = new AppendRedirectCatCommand();
        tempDirectory = Files.createTempDirectory("testDir");
        file1 = new File(tempDirectory.toFile(), "file1.txt");
        file2 = new File(tempDirectory.toFile(), "file2.txt");
        Files.writeString(file1.toPath(), "Hello, World2!"); // Initial content for file1
        Files.writeString(file2.toPath(), "Hello, World!\n"); // Initial content for file1
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(file1.toPath());
        Files.deleteIfExists(file2.toPath());
        Files.deleteIfExists(tempDirectory);
    }

    @Test
    public void testAppendToExistingFile() throws IOException {
        String[] args = {"file1.txt", ">>", "file2.txt"};
        command.execute(tempDirectory.toFile(), args);

        assertTrue(file2.exists(), "file2.txt should exist after appending");
        String file2Content = Files.readString(file2.toPath());
        assertEquals("Hello, World!\nHello, World2!", file2Content.trim(), "file2.txt should contain content from file1");
    }

    @Test
    public void testCreateAndAppendToNewFile() {
        String[] args = {"file1.txt", ">>", "file2.txt"};
        if (file2.exists()) file2.delete();  // Ensure file2 does not exist

        command.execute(tempDirectory.toFile(), args);

        assertTrue(file2.exists(), "file2.txt should be created if it doesn't exist");
        assertDoesNotThrow(() -> {
            String file2Content = Files.readString(file2.toPath());
            assertEquals("Hello, World2!", file2Content.trim(), "file2.txt should contain content from file1 after creation");
        });
    }

    @Test
    public void testInvalidFileRead() {
        File nonExistentFile = new File(tempDirectory.toFile(), "nonexistent.txt");
        String[] args = {"nonexistent.txt", ">>", "file2.txt"};

        command.execute(tempDirectory.toFile(), args);

        assertFalse(nonExistentFile.exists(), "file2.txt should not exist if file1 does not exist");
    }
}
