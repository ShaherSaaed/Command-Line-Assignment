package commands;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RedirectLsACommandTest {
    private RedirectLsACommand command;
    private Path tempDirectory;
    private File outputFile;
    private File file1;
    private File file2;
    private File hiddenFile; // Declare hiddenFile here

    @BeforeEach
    public void setUp() throws IOException {
        command = new RedirectLsACommand();
        tempDirectory = Files.createTempDirectory("testDir");

        // Create some files in the temp directory
        file1 = new File(tempDirectory.toFile(), "file1.txt");
        file2 = new File(tempDirectory.toFile(), "file2.txt");
        outputFile = new File(tempDirectory.toFile(), "output.txt");

        // Create the hidden file
        hiddenFile = new File(tempDirectory.toFile(), ".hiddenFile.txt");
        hiddenFile.createNewFile();

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            Files.setAttribute(hiddenFile.toPath(), "dos:hidden", true);
        }

        file1.createNewFile();
        file2.createNewFile();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete the created files
        Files.deleteIfExists(file1.toPath());
        Files.deleteIfExists(file2.toPath());
        Files.deleteIfExists(outputFile.toPath());
        Files.deleteIfExists(hiddenFile.toPath()); // Delete the hidden file
        Files.deleteIfExists(tempDirectory);
    }

    @Test
    public void testExecuteCreatesAndWritesToFile() throws IOException {
        if (outputFile.exists()) {
            outputFile.delete();
        }
        String[] args = {">", "output.txt"};
        command.execute(tempDirectory.toFile(), args);

        assertTrue(outputFile.exists(), "output.txt should be created by the command");

        String outputContent = Files.readString(outputFile.toPath()).trim();

        assertTrue(outputContent.contains("file1.txt"), "output.txt should contain file1.txt");
        assertTrue(outputContent.contains("file2.txt"), "output.txt should contain file2.txt");
    }

    @Test
    public void testRedirectToFile() throws IOException {
        Files.writeString(outputFile.toPath(), "Initial Content\n");

        String[] args = {">", "output.txt"};
        command.execute(tempDirectory.toFile(), args);

        String outputContent = Files.readString(outputFile.toPath());

        assertFalse(outputContent.startsWith("Initial Content"), "output.txt should retain the initial content at the start");
        assertTrue(outputContent.contains("file1.txt"), "output.txt should contain file1.txt appended after initial content");
        assertTrue(outputContent.contains("file2.txt"), "output.txt should contain file2.txt appended after initial content");
    }

    @Test
    public void testErrorHandlingForInvalidDirectory() {
        String[] args = {">", "output.txt"};
        File invalidDirectory = new File("invalidDir"); // Non-existent directory

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        command.execute(invalidDirectory, args);

        String output = outputStream.toString();
        assertTrue(output.contains("Error creating file"), "Error message should be printed for invalid directory");

        System.setOut(System.out); // Reset System.out
    }

    @Test
    public void testHiddenFileIsListed() throws IOException {
        // Setup the output file path for redirection
        String[] args = {">", "output.txt"};

        // Execute the command to list files, redirecting to outputFile
        command.execute(tempDirectory.toFile(), args);

        // Read the contents of the output file
        String outputContent = Files.readString(outputFile.toPath()).trim();

        // Verify that the hidden file is listed in the output
        assertTrue(outputContent.contains(".hiddenFile.txt"), "output.txt should contain .hiddenFile.txt");
    }
}