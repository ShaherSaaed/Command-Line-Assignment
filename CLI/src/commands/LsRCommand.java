package commands;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LsRCommand {
    public void execute(File currentDirectory, String[] args) {
        File directory = new File(String.valueOf(currentDirectory));
        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = files.length - 1; i >= 0; i--) {
                if (files[i].isHidden()) {
                    continue;
                } else
                    System.out.println(files[i].getName());
            }
        }
    }
    public void execute(File currentDirectory, OutputStream outputStream) {
        File[] files = currentDirectory.listFiles();
        if (files == null) {
            System.out.println("No files found.");
            return;
        }
        List<File> fileList = Arrays.asList(files);
        Collections.reverse(fileList);
        try {
            for (File file : fileList) {
                outputStream.write((file.getName() + "\n").getBytes());
            }
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Error writing reversed files to output stream: " + e.getMessage());
        }
    }
}
