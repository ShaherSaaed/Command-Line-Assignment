package commands;

import java.io.File;

public class RmCommand {
    public void execute(File currentDirectory, String[] args) {
        if (args.length == 0) {
            System.out.println("Filename required for rm command");
            return;
        }

        File fileToDelete = new File(currentDirectory, args[0]);
        if (fileToDelete.exists() && fileToDelete.isFile()) {
            if (fileToDelete.delete()) {
                System.out.println("File deleted: " + fileToDelete.getAbsolutePath());
            } else {
                System.out.println("Failed to delete file: " + fileToDelete.getAbsolutePath());
            }
        } else {
            System.out.println("File not found or it is a directory: " + args[0]);
        }
    }
}
