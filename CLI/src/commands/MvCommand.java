package commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class MvCommand {
    public void execute(File currentDirectory, String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Missing source or destination path.");
            return;
        }
        if (!currentDirectory.isDirectory()) {
            System.out.println("Error: Provided current directory is not a directory: " + currentDirectory.getAbsolutePath());
            return;
        }
        Path baseDir = currentDirectory.toPath().toAbsolutePath();
        Path source = baseDir.resolve(args[0]).normalize();
        Path destination = baseDir.resolve(args[1]).normalize();
        if (!Files.exists(source)) {
            System.out.println("Error: Source file does not exist at " + source);
            return;
        }
        try {
            if (Files.isDirectory(destination)) {
                destination = destination.resolve(source.getFileName());
                System.out.println("Moving to directory, new destination is: " + destination);
            }

            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File moved successfully from " + source + " to " + destination);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
