package commands;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
public class LsCommand {
    public void execute(File currentDirectory, String[] args) {
        File directory = new File(String.valueOf(currentDirectory));
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isHidden())
                    continue;
                else
                    System.out.println(file.getName());
            }
        }
    }
    public void execute(File currentDirectory, OutputStream outputStream) {
        File[] files = currentDirectory.listFiles();
        if (files == null) {
            System.out.println("No files found.");
            return;
        }
        try {
            for (File file : files) {
                outputStream.write((file.getName() + "\n").getBytes());
            }
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Error writing files to output stream: " + e.getMessage());
        }
    }
}