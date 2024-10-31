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
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
public class RedirectLsRCommandTest {
    private File tempDirectory;
    private LsRCommand lsRCommand;
    private String args[] = {"-r"};
    @BeforeEach
    public void setUp() throws IOException {
        tempDirectory = Files.createTempDirectory("testDir").toFile();
        new File(tempDirectory, "file1.txt").createNewFile();
        new File(tempDirectory, "file2.txt").createNewFile();
        new File(tempDirectory, "file3.txt").createNewFile();
        File hiddenFile = new File(tempDirectory, ".hiddenFile.txt");
        hiddenFile.createNewFile();
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            Files.setAttribute(hiddenFile.toPath(), "dos:hidden", true);
        }
        lsRCommand = new LsRCommand();
    }
    @AfterEach
    public void tearDown() {
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
                String[] lines = new String(buf, off, len).trim().split(System.lineSeparator());
                for (String line : lines) {
                    if (!line.isEmpty()) {
                        output.add(line.trim());
                    }
                }
            }
        }));
        lsRCommand.execute(tempDirectory, args);
        List<String> expectedOrder = List.of("file3.txt", "file2.txt", "file1.txt");
        assertEquals(expectedOrder, output);
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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        lsRCommand.execute(tempDirectory, args);
        String[] lines = outputStream.toString().trim().split(System.lineSeparator());
        output.addAll(Arrays.asList(lines));
        lsRCommand.execute(tempDirectory, new String[]{"-r"});
        List<String> expectedOrder = List.of("file3.txt", "file2.txt", "file1.txt");
        assertEquals(expectedOrder, output, "The files should be listed in reverse order as in 'ls -r'");
    }
}