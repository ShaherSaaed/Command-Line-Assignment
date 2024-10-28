package commands;

import java.io.File;

public class MkdirCommand {
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: Missing directory name.");
            return;
        }

        File dir = new File(args[0]);
        if (dir.mkdir()) {
            System.out.println("Directory created: " + args[0]);
        } else {
            System.out.println("Failed to create directory: " + args[0]);
        }
    }
}
