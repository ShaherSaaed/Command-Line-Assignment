package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CatCommand {
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: Missing file name.");
            return;
        }//reading
        String content = "";
        File file = new File(args[0]);
        try{
            content = Files.readString(Paths.get(args[0])).replace("\n", " ").replace("\r", " ");
            System.out.println(content);
        }
        catch (IOException e){
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
}
