package commands;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class AppendRedirectCatCommand {
    public void execute(File currentDirectory, String[] args) { 
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
            try (FileWriter writer = new FileWriter(file2Path.toFile(), true)) {
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
    public void execute(File currentDirectory, String[] args, InputStream inputStream) {
    if (args[2].equals(">>")) {
            String fileName = args[3];
            Path filePath = currentDirectory.toPath().resolve(fileName);
            File file = new File(filePath.toString());

            if (!file.exists()) {
                try {
                    file.createNewFile();
                    System.out.println("Created new file: " + fileName);
                } catch (IOException e) {
                    System.out.println("Error creating file " + fileName + ": " + e.getMessage());
                    return;
                }
            }
            try (FileWriter writer = new FileWriter(file, true);
                 BufferedReader reader = inputStream != null
                         ? new BufferedReader(new InputStreamReader(inputStream))
                         : new BufferedReader(new InputStreamReader(System.in))) {

                System.out.println("Enter content to append (end with ']'):");
                String line;
                while ((line = reader.readLine()) != null && !line.equals("]")) {
                    writer.write(line + System.lineSeparator());
                    System.out.println("Content added to: " + fileName);
                }
            } catch (IOException e) {
                System.out.println("Error writing to " + fileName + ": " + e.getMessage());
            }
        }
    }
}
