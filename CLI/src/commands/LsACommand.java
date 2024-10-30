package commands;

import java.io.File;

public class LsACommand {
    public void execute(File currentDirectory, String[] args) {
//        String directoryPath = "."; //Needs to be changed to match current working directory
        File directory = new File(String.valueOf(currentDirectory));
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }
}
