package commands;

import java.io.File;
import java.io.IOException;

public class TouchCommand {
    public void execute(File currentDirectory, String[] args) {
        if (args.length == 0) {
            System.out.println("Filename required for touch command");
            return;
        }
        for(String s:args) {
            File newFile = new File(currentDirectory, s);
            try {
                if (newFile.createNewFile()) {
                    System.out.println("File created: " + newFile.getAbsolutePath());
                } else {
                    System.out.println("File already exists: " + newFile.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
    }
}
