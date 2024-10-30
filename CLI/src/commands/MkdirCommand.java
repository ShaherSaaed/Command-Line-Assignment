package commands;

import java.io.File;

public class MkdirCommand {
    public void execute(File currentDirectory, String[] args) {
        if (args.length == 0) {
            System.out.println("Directory name required for mkdir command");
            return;
        }

        File newDir = new File(currentDirectory, args[0]);
        if (newDir.mkdir()) {
            System.out.println("Directory created: " + newDir.getAbsolutePath());
        } else {
            System.out.println("Failed to create directory or it already exists: " + newDir.getAbsolutePath());
        }
    }
}
