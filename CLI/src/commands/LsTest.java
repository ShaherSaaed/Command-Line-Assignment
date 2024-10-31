package commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LsTest {

    private File tempDirectory;
    private LsCommand lsCommand;

    @BeforeEach
    public void setUp() throws IOException {
        tempDirectory = Files.createTempDirectory("testDir").toFile();

        // Create some files in the temporary directory
        new File(tempDirectory, "file1.txt").createNewFile();
        new File(tempDirectory, "file2.txt").createNewFile();
        new File(tempDirectory, "file3.txt").createNewFile();

        // Create a hidden file
        File hiddenFile = new File(tempDirectory, ".hiddenFile.txt");
        hiddenFile.createNewFile();

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            Files.setAttribute(hiddenFile.toPath(), "dos:hidden", true);
        }

        lsCommand = new LsCommand();
    }

    @AfterEach
    public void tearDown() {
        for (File file : tempDirectory.listFiles()) {
            file.delete();
        }
        tempDirectory.delete();
    }

    @Test
    public void testExecuteIgnoresHiddenFiles() {
        List<String> output = new ArrayList<>();
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream() {
            public void write(byte[] buf, int off, int len) {
                output.add(new String(buf, off, len).trim());
            }
        }));

        lsCommand.execute(tempDirectory, null);

        assertTrue(output.contains("file1.txt"));
        assertTrue(output.contains("file2.txt"));
        assertTrue(!output.contains(".hiddenFile.txt"));
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
        lsCommand.execute(tempDirectory, null);

        // Expected order should be reverse of creation order
        List<String> expectedOrder = List.of("file1.txt", "file2.txt", "file3.txt");

        // Check that the output matches the expected reversed order
        assertEquals(expectedOrder, output);
    }
}
