package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CatCommand {
    public void execute(File currentDirectory, String[] args) {
        if (args.length < 1) {
            System.out.println("Error: Missing file name.");
            return;
        }
        Path filePath = currentDirectory.toPath().resolve(args[0]);
        try{
            String content = Files.readString(filePath);
            System.out.println(content);
        }
        catch (IOException e){
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
}