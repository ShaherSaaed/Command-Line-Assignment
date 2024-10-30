package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class CatCommand {
    public void execute(File currentDirectory, String[] args) {
        if (args.length < 1) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            System.out.println(input);
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