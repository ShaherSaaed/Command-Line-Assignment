package commands;

import java.io.File;

public class RmdirCommand {
    public void execute(File currentDirectory, String[] args) {
        if (args.length == 0) {
            System.out.println("Directory name required for rmdir command");
            return;
        }

        File dirToDelete = new File(currentDirectory, args[0]);
        if (dirToDelete.exists() && dirToDelete.isDirectory()) {
            if (dirToDelete.delete()) {
                System.out.println("Directory deleted: " + dirToDelete.getAbsolutePath());
            } else {
                System.out.println("Failed to delete directory: " + dirToDelete.getAbsolutePath() + " (directory may not be empty)");
            }
        } else {
            System.out.println("Directory not found or it is a file: " + args[0]);
        }
    }
}
