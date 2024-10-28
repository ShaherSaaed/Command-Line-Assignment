package commands;

import java.io.File;
import java.io.IOException;

public class TouchCommand {
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: Missing file name.");
            return;
        }
        File file = new File(args[0]);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + args[0]);
            } else {
                System.out.println("File already exists: " + args[0]);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }
}
