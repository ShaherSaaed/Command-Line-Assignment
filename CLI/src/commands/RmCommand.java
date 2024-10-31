package commands;

import java.io.File;
import java.util.Arrays;

public class RmCommand {
    public void execute(File currentDirectory, String[] args) {
        if (args.length == 0) {
            System.out.println("Filename required for rm command");
            return;
        }
        if(args[0].equals("-r")){
            String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
            new RmdirCommand().execute(currentDirectory, commandArgs);
        }else{
            for (String s : args) {
                File fileToDelete = new File(currentDirectory, s);
                if (fileToDelete.exists() && fileToDelete.isFile()) {
                    if (fileToDelete.delete()) {
                        System.out.println("File deleted: " + fileToDelete.getAbsolutePath());
                    } else {
                        System.out.println("Failed to delete file: " + fileToDelete.getAbsolutePath());
                    }
                } else {
                    System.out.println("File not found or it is a directory: " + s);
                }
            }
        }
    }
}
