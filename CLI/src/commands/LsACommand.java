package commands;

import java.io.File;

public class LsACommand {
    public void execute(String[] args) {
        String directoryPath = "."; //Needs to be changed to match current working directory
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }
}
