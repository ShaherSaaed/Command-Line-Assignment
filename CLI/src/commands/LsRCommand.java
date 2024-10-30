package commands;

import java.io.File;

public class LsRCommand {
    public void execute(File currentDirectory, String[] args) {
        File directory = new File(String.valueOf(currentDirectory));
        File[] files = directory.listFiles();

        if (files != null) {
            for (int i = files.length - 1; i >= 0; i--) {
                if (files[i].isHidden()) {
                    continue;
                } else
                    System.out.println(files[i].getName());
            }
        }
    }
}
