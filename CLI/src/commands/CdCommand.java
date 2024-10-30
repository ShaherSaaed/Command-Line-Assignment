package commands;

import java.io.File;

public class CdCommand {
    private static File currentDirectory = new File(System.getProperty("user.dir"));

    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println(currentDirectory.getAbsolutePath());
            return;
        }
        String path = args[0];
        File newDirectory;
        if (path.equals("..")) {
            newDirectory = currentDirectory.getParentFile();
        } else {
            newDirectory = new File(currentDirectory, path);
        }

        if (newDirectory != null && newDirectory.exists() && newDirectory.isDirectory()) {
            currentDirectory = newDirectory;
            System.out.println("Changed directory to: " + currentDirectory.getAbsolutePath());
        } else {
            System.out.println("Directory not found: " + path);
        }
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }
}
