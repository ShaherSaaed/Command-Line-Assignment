package commands;

import java.io.File;

public class PwdCommand {
    public void execute(File currentDirectory) {
        System.out.println(currentDirectory.getAbsolutePath());
    }
}
