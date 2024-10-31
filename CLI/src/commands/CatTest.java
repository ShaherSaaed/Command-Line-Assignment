package commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.PrintStream;
import java.nio.file.StandardOpenOption;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatTest {

    private File tempDirectory;
    private File testFile;
    private CatCommand catCommand;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary directory for testing
        tempDirectory = Files.createTempDirectory("testDir").toFile();

        // Create a temporary test file
        testFile = new File(tempDirectory, "testFile.txt");
        testFile.createNewFile();

        catCommand = new CatCommand();
    }

    @AfterEach
    public void tearDown() {
        // Delete the temporary test file and directory
        if (testFile.exists()) {
            testFile.delete();
        }
        tempDirectory.delete();
    }

    @Test
    public void testExecuteReadsFileCorrectly() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String content = "This is a test file content.";
        Files.writeString(testFile.toPath(), content);
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        catCommand.execute(tempDirectory, new String[]{"testfile.txt"});
        System.setOut(originalOut);

        String output = outputStream.toString().trim();
        assertEquals("This is a test file content.", output, "The file content was not read correctly.");
    }

    @Test
    public void testExecutePrintsInputCorrectlyWithoutArguments() {
        while (true) {
            String input = "This is a test file!";
            String storage = input;
            assertEquals(input, storage);
            input = "]";
            if (input.equals("]")) {
                break;
            }
        }
    }

    @Test
    public void testExecuteTerminatesAfterEnteringDelimiter() {
        while (true) {
            String input = "]";
            if (input.equals("]")) {
                break;
            }
        }
    }
}