package commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
public class AppendRedirectLsRCommandTest {
    private File tempDirectory;
    private LsRCommand lsRCommand;
    private String args[] = {"-r"};
    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary directory for testing
        tempDirectory = Files.createTempDirectory("testDir").toFile();
        // Create some visible files in the temporary directory
        new File(tempDirectory, "file1.txt").createNewFile();
        new File(tempDirectory, "file2.txt").createNewFile();
        new File(tempDirectory, "file3.txt").createNewFile();
        // Create a hidden file in the temporary directory
        File hiddenFile = new File(tempDirectory, ".hiddenFile.txt");
        hiddenFile.createNewFile();
        // Set file as hidden if on Windows
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            Files.setAttribute(hiddenFile.toPath(), "dos:hidden", true);
        }
        lsRCommand = new LsRCommand();
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
    public void testExecuteListsFilesInReverseOrder() {
        List<String> output = new ArrayList<>();
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream() {
            public void write(byte[] buf, int off, int len) {
                String str = new String(buf, off, len).trim();
                if (!str.isEmpty()) {  // Avoid adding empty strings
                    output.add(str);
                }
            }
        }));
        lsRCommand.execute(tempDirectory, args);
        List<String> expectedOrder = List.of("file3.txt", "file2.txt", "file1.txt");
        assertEquals(expectedOrder, output, "The files should be listed in reverse order as in 'ls -r'");
    }

    @Test
    public void testExecuteIgnoresHiddenFiles() {
        List<String> output = new ArrayList<>();
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream() {
            public void write(byte[] buf, int off, int len) {
                output.add(new String(buf, off, len).trim());
            }
        }));
        lsRCommand.execute(tempDirectory, args);
        assertFalse(output.contains(".hiddenFile.txt"));
    }
    @Test
    public void testLsRCommandReverseOrder() {
        List<String> output = new ArrayList<>();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        lsRCommand.execute(tempDirectory, new String[]{"-r"}); // Simulating "ls -r"
        String[] outputLines = outContent.toString().split(System.lineSeparator());
        for (String line : outputLines) {
            if (!line.trim().isEmpty()) {
                output.add(line.trim());
            }
        }
        List<String> expectedOrder = List.of("file3.txt", "file2.txt", "file1.txt");
        assertEquals(expectedOrder, output, "The files should be listed in reverse order as in 'ls -r'");
    }

}