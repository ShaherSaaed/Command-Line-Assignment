package commands;

import java.io.File;

public class RmdirCommand {
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: Missing directory name.");
            return;
        }

        File dir = new File(args[0]);
        if (dir.isDirectory() && dir.delete()) {
            System.out.println("Directory removed: " + args[0]);
        }else {
            System.out.println("Failed to remove directory: " + args[0] + " (Make sure it is empty)");
        }
    }
}
