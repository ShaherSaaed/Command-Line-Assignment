package commands;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class PipeCommand {
    public void execute(File currentDirectory, String[] commandArgs) {
        if (commandArgs.length < 2) {
            System.out.println("Usage: <command1> | <command2>");
            return;
        }
        try {
            if ("ls".equals(commandArgs[0])) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                new LsCommand().execute(currentDirectory, outputStream);
                InputStream lsOutput = new ByteArrayInputStream(outputStream.toByteArray());
                if ("sort".equals(commandArgs[1])) {
                    SortCommand sortCommand = new SortCommand();
                    sortCommand.execute(lsOutput);
                } else {
                    System.out.println("Unknown command in pipe sequence: " + commandArgs[1]);
                }
            } else if ("-a".equals(commandArgs[0]) && "ls".equals(commandArgs[1])) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                new LsACommand().execute(currentDirectory, outputStream);
                InputStream lsOutput = new ByteArrayInputStream(outputStream.toByteArray());
                if ("sort".equals(commandArgs[2])) {
                    SortCommand sortCommand = new SortCommand();
                    sortCommand.execute(lsOutput);
                } else {
                    System.out.println("Unknown command in pipe sequence: " + commandArgs[2]);
                }
            } else if ("-r".equals(commandArgs[0]) && "ls".equals(commandArgs[1])) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                new LsRCommand().execute(currentDirectory, outputStream);
                InputStream lsOutput = new ByteArrayInputStream(outputStream.toByteArray());
                if ("sort".equals(commandArgs[2])) {
                    SortCommand sortCommand = new SortCommand();
                    sortCommand.execute(lsOutput);
                } else {
                    System.out.println("Unknown command in pipe sequence: " + commandArgs[2]);
                }
            }else if ("cat".equals(commandArgs[1])) {
                File fileToRead = new File(currentDirectory, commandArgs[0]);
                if (!fileToRead.exists()) {
                    System.out.println("File not found: " + commandArgs[1]);
                    return;
                }
                InputStream catOutput = getInputStream(fileToRead);
                if ("sort".equals(commandArgs[2])) {
                    SortCommand sortCommand = new SortCommand();
                    sortCommand.execute(catOutput);
                } else {
                    System.out.println("Unknown command in pipe sequence: " + commandArgs[2]);
                }
            } else {
                System.out.println("Unknown command in pipe sequence: " + commandArgs[0]);
            }
        } catch (IOException e) {
            System.out.println("Error executing piped commands: " + e.getMessage());
        }
    }

    private static InputStream getInputStream(File fileToRead) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (FileInputStream fileInputStream = new FileInputStream(fileToRead)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
