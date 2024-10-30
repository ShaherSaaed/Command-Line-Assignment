package commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class LsCommand {
    public void execute(File currentDirectory, String[] args) {
        File directory = new File(String.valueOf(currentDirectory));
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
