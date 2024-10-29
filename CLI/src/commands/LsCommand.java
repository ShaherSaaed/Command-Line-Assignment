package commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class LsCommand {
    public void execute(String[] args) {
        String directoryPath = "."; //Needs to be changed to match current working directory
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isHidden())
                    continue;
                else
                    System.out.println(file.getName());
            }
        }
    }
}
