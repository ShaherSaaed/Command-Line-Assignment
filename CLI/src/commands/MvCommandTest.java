package commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MvCommandTest {
    private Path tempDir;
    private Path sourceFile;
    private Path targetDir;
    private Path targetFile;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("testDir");
        sourceFile = Files.createFile(tempDir.resolve("sourceFile.txt"));
        targetDir = Files.createDirectory(tempDir.resolve("targetDir"));
        targetFile = tempDir.resolve("targetFile.txt");
    }
    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(sourceFile);
        Files.deleteIfExists(targetDir.resolve("sourceFile.txt"));
        Files.deleteIfExists(targetFile);
        Files.deleteIfExists(targetDir);
    }
    @Test
    void testMoveToNewDirectory() throws IOException {
        MvCommand mvCommand = new MvCommand();
        File currentDirectory = tempDir.toFile();
        String[] args = {"sourceFile.txt", "targetDir"};
        System.out.println("Running testMoveToNewDirectory...");
        mvCommand.execute(currentDirectory, args);
        assertTrue(Files.exists(targetDir.resolve("sourceFile.txt")), "File should be moved to the target directory");
        assertFalse(Files.exists(sourceFile), "Source file should no longer exist after moving");
    }

    @Test
    void testRenameWithinSameDirectory() throws IOException {
        Path targetFile = tempDir.resolve("renamedFile.txt");
        MvCommand mvCommand = new MvCommand();
        File currentDirectory = tempDir.toFile();
        String[] args = {"sourceFile.txt", "renamedFile.txt"};
        System.out.println("Before renaming:");
        System.out.println("Source file exists: " + Files.exists(sourceFile));
        System.out.println("Target file exists: " + Files.exists(targetFile));
        mvCommand.execute(currentDirectory, args);
        assertTrue(Files.exists(targetFile), "File should be renamed in the same directory");
        assertFalse(Files.exists(sourceFile), "Source file should no longer exist after renaming");
    }
    @Test
    void testSourceFileDoesNotExist() {
        MvCommand mvCommand = new MvCommand();
        File currentDirectory = tempDir.toFile();
        String[] args = {"nonExistentFile.txt", "targetFile.txt"};
        mvCommand.execute(currentDirectory, args);
        assertFalse(Files.exists(targetFile), "Target file should not be created if source does not exist");
    }
    @Test
    void testOverwriteExistingFile() throws IOException {
        Path existingFile = Files.createFile(tempDir.resolve("targetFile.txt"));
        MvCommand mvCommand = new MvCommand();
        File currentDirectory = tempDir.toFile();
        String[] args = {"sourceFile.txt", "targetFile.txt"};
        System.out.println("Before overwriting:");
        System.out.println("Source file exists: " + Files.exists(sourceFile));
        System.out.println("Existing file (target) exists: " + Files.exists(existingFile));
        mvCommand.execute(currentDirectory, args);
        assertTrue(Files.exists(existingFile), "Target file should exist after move");
        assertFalse(Files.exists(sourceFile), "Source file should no longer exist after overwriting");
    }
    @Test
    void testMoveToDirectoryKeepsOriginalName() throws IOException {
        MvCommand mvCommand = new MvCommand();
        File currentDirectory = tempDir.toFile();
        String[] args = {"sourceFile.txt", "targetDir"};
        System.out.println("Running testMoveToDirectoryKeepsOriginalName...");
        mvCommand.execute(currentDirectory, args);
        assertTrue(Files.exists(targetDir.resolve("sourceFile.txt")), "File should keep the original name in the target directory");
        assertFalse(Files.exists(sourceFile), "Source file should no longer exist after moving");
    }
}
