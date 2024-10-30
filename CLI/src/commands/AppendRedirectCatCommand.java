package commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class AppendRedirectCommand {
    public void execute(File currentDirectory, String[] args) { //index of first file is 0, second file is 2
        if (args[1].equals(">>")) {
            String file1Name = args[0];
            String file2Name = args[2];
            String content = "";

            Path file1Path = currentDirectory.toPath().resolve(file1Name);
            Path file2Path = currentDirectory.toPath().resolve(file2Name);

            File file1 = new File(file1Name);
            File file2 = new File(file2Name);

            if (!file2.exists()) {
                try {
                    file2.createNewFile();
                    System.out.println("Created new file: " + file2Name);
                } catch (IOException e) {
                    System.out.println("Error creating file " + file2Name + ": " + e.getMessage());
                    return;
                }
            }

            try {
                content = Files.readString(file1Path);
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file");
            }
            try (FileWriter writer = new FileWriter(file2Path.toFile(), true)) { // true enables append mode
                writer.write(content);
                System.out.println("Content from " + file1Name + " appended to " + file2Name);
            } catch (IOException e) {
                System.out.println("Error writing to " + file2Name + ": " + e.getMessage());
            }
            System.out.println(content);
        } else if (args[0].equals(">>")) {
            String fileName = args[1];

            File file = new File(fileName);
            Path filePath = currentDirectory.toPath().resolve(fileName);

            if (!file.exists()) {
                try {
                    file.createNewFile();
                    System.out.println("Created new file: " + fileName);
                } catch (IOException e) {
                    System.out.println("Error creating file " + fileName + ": " + e.getMessage());
                    return;
                }
            }
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String content = scanner.nextLine();
                if (content.equals("]"))
                    break;
                try (FileWriter writer = new FileWriter(filePath.toFile(), true)) { // true enables append mode
                    writer.write(content);
                    System.out.println("Content added to: " + fileName);
                } catch (IOException e) {
                    System.out.println("Error writing to " + fileName + ": " + e.getMessage());
                }
            }

        }
    }
}
