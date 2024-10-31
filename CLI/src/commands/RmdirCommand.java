package commands;

import java.io.File;
import java.util.Scanner;

public class RmdirCommand {
    public void execute(File currentDirectory, String[] args) {
        if (args.length == 0) {
            System.out.println("Directory name required for rmdir command");
            return;
        }
        for (String s : args) {
            File dirToDelete = new File(currentDirectory, s);
            if (dirToDelete.exists() && dirToDelete.isDirectory()) {
                if (dirToDelete.delete()) {
                    System.out.println("Directory deleted: " + dirToDelete.getAbsolutePath());
                } else {
                    if (dirToDelete.list().length > 0) {
                            deleteDirectoryRecursively(dirToDelete);
                            System.out.println("Directory and its contents deleted: " + dirToDelete.getAbsolutePath());
                    } else {
                        System.out.println("Failed to delete directory: " + dirToDelete.getAbsolutePath());
                    }
                }
            } else {
                System.out.println("Directory not found or it is a file: " + s);
            }
        }
    }
    private void deleteDirectoryRecursively(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectoryRecursively(file);
            }
        }
        directory.delete();
    }
}
