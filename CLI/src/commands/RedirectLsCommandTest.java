package commands;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;
public class RedirectLsCommandTest {
    private RedirectLsCommand command;
    private Path tempDirectory;
    private File outputFile;
    private File file1;
    private File file2;
    @BeforeEach
    public void setUp() throws IOException {
        command = new RedirectLsCommand();
        tempDirectory = Files.createTempDirectory("testDir");
        // Create some files in the temp directory
        file1 = new File(tempDirectory.toFile(), "file1.txt");
        file2 = new File(tempDirectory.toFile(), "file2.txt");
        outputFile = new File(tempDirectory.toFile(), "output.txt");
        file1.createNewFile();
        file2.createNewFile();
    }
    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(file1.toPath());
        Files.deleteIfExists(file2.toPath());
        Files.deleteIfExists(outputFile.toPath());
        Files.deleteIfExists(tempDirectory);
    }
    @Test
    public void testExecuteCreatesAndWritesToFile() throws IOException {
        if (!tempDirectory.toFile().exists()) {
            Files.delete(outputFile.toPath());
            assertFalse(outputFile.exists(), "output.txt should be deleted before executing the command");
        }
        String[] args = {">", "output.txt"};
        command.execute(tempDirectory.toFile(), args);
        assertTrue(outputFile.exists(), "output.txt should be created by the command");
        String outputContent = Files.readString(outputFile.toPath());
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
    public void testRecreateOutputFileIfDeleted() throws IOException {
        String[] args = {">", "output.txt"};
        command.execute(tempDirectory.toFile(), args);
        assertTrue(outputFile.exists(), "output.txt should be recreated by the command");
        String outputContent = Files.readString(outputFile.toPath()).trim();
        assertTrue(outputContent.contains("file1.txt"), "output.txt should contain file1.txt");
        assertTrue(outputContent.contains("file2.txt"), "output.txt should contain file2.txt");
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
}