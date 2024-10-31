package commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class PipeCommandTest {
    private File tempDir;
    private File tempFile1;
    private File tempFile2;

    @Before
    public void setUp() throws IOException {
        tempDir = new File("tempTestDir");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        tempFile1 = new File(tempDir, "test1.txt");
        try (FileWriter writer = new FileWriter(tempFile1)) {
            writer.write("apple\nbanana\ncherry");
        }
        tempFile2 = new File(tempDir, "test2.txt");
        try (FileWriter writer = new FileWriter(tempFile2)) {
            writer.write("date\nelderberry\nfig");
        }
    }
    @After
    public void tearDown() {
        if (tempFile1.exists()) tempFile1.delete();
        if (tempFile2.exists()) tempFile2.delete();
        if (tempDir.exists()) tempDir.delete();
    }
    @Test
    public void testLsSort() {
        PipeCommand pipeCommand = new PipeCommand();
        String[] commandArgs = {"ls", "sort"};
        pipeCommand.execute(tempDir, commandArgs);
    }

    @Test
    public void testCatSort() {
        PipeCommand pipeCommand = new PipeCommand();
        String[] commandArgs = {"test1.txt", "cat", "sort"};
        pipeCommand.execute(tempDir, commandArgs);
    }

    @Test
    public void testUnknownCommand() {
        PipeCommand pipeCommand = new PipeCommand();
        String[] commandArgs = {"unknown", "command"};
        pipeCommand.execute(tempDir, commandArgs);
    }
}
