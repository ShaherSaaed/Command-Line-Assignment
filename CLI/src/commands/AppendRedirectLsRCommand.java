package commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppendRedirectLsRCommand {
    public void execute(File currentDirectory, String[] args) {
        String outputFileName = args[1];
        File outputFile = new File(currentDirectory, outputFileName);

        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
                System.out.println("Created new file: " + outputFileName);
            } catch (IOException e) {
                System.out.println("Error creating file " + outputFileName + ": " + e.getMessage());
                return;
            }
        }

        try (FileWriter writer = new FileWriter(outputFile, true)) {
            File[] files = currentDirectory.listFiles();

            if (files != null) {
                for(int i = files.length - 1; i >= 0; i--) {
                    if (!files[i].isHidden()) {
                        writer.write(files[i].getName() + System.lineSeparator());
                    }
                }
            }
            System.out.println("Directory listing written to " + outputFileName);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to: " + outputFileName);
        }
    }
}