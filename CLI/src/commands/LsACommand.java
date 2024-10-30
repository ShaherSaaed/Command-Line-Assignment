package commands;

import java.io.File;

public class LsACommand {
    public void execute(File currentDirectory, String[] args) {
        File directory = new File(String.valueOf(currentDirectory));
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }
}
