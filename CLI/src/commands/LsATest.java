package commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LsATest {

    private File tempDirectory;
    private LsACommand lsACommand;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary directory for testing
        tempDirectory = Files.createTempDirectory("testDir").toFile();

        // Create some files in the temporary directory
        new File(tempDirectory, "file1.txt").createNewFile();
        new File(tempDirectory, "file2.txt").createNewFile();
        new File(tempDirectory, "file3.txt").createNewFile();

        // Create a hidden file
        File hiddenFile = new File(tempDirectory, ".hiddenFile.txt");
        hiddenFile.createNewFile();

        // Set file as hidden if on Windows
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            Files.setAttribute(hiddenFile.toPath(), "dos:hidden", true);
        }

        lsACommand = new LsACommand();
    }

    @AfterEach
    public void tearDown() {
        // Delete the temporary directory and all its contents
        for (File file : tempDirectory.listFiles()) {
            file.delete();
        }
        tempDirectory.delete();
    }

    @Test
    public void testExecuteListsAllFiles() {
        // Capture the output of the execute method
        List<String> output = new ArrayList<>();
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream() {
            public void write(byte[] buf, int off, int len) {
                output.add(new String(buf, off, len).trim());
            }
        }));

        // Execute ls -a command on the temporary directory
        lsACommand.execute(tempDirectory, null);

        // Check that the output contains both visible and hidden files
        assertTrue(output.contains("file1.txt"));
        assertTrue(output.contains("file2.txt"));
        assertTrue(output.contains("file3.txt"));
        assertTrue(output.contains(".hiddenFile.txt"));
    }
    @Test
    public void testExecuteListsFilesInCorrectOrder() {
        // Capture the output of the execute method
        List<String> output = new ArrayList<>();
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream() {
            public void write(byte[] buf, int off, int len) {
                output.add(new String(buf, off, len).trim());
            }
        }));

        // Execute the command on the temporary directory
        lsACommand.execute(tempDirectory, null);

        // Expected order should be reverse of creation order
        List<String> expectedOrder = List.of(".hiddenFile.txt", "file1.txt", "file2.txt", "file3.txt");

        // Check that the output matches the expected reversed order
        assertEquals(expectedOrder, output);
    }
}
