package commands;

import java.io.*;

public class PipeCommand {
    public void execute(File currentDirectory, String[] commandArgs) {
        if (commandArgs.length < 3) { // At least three arguments are needed to have a pipe sequence
            System.out.println("Usage: <command1> | <command2> ... | <commandN>");
            return;
        }

        try {
            InputStream initialOutput;

            // Check if the first command is "ls" or one of its variations
            if ("ls".equals(commandArgs[0])) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                new LsCommand().execute(currentDirectory, outputStream);
                initialOutput = new ByteArrayInputStream(outputStream.toByteArray());
            } else if ("-a".equals(commandArgs[0]) && "ls".equals(commandArgs[1])) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                new LsACommand().execute(currentDirectory, outputStream);
                initialOutput = new ByteArrayInputStream(outputStream.toByteArray());
            } else if ("-r".equals(commandArgs[0]) && "ls".equals(commandArgs[1])) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                new LsRCommand().execute(currentDirectory, outputStream);
                initialOutput = new ByteArrayInputStream(outputStream.toByteArray());
            } else {
                System.out.println("Unknown command in pipe sequence: " + commandArgs[0]);
                return;
            }
            if(commandArgs.length==3) {
                handlePipedCommands(currentDirectory, commandArgs, initialOutput, 2);
            }else {
                handlePipedCommands(currentDirectory, commandArgs, initialOutput, 1);
            }

        } catch (IOException e) {
            System.out.println("Error executing piped commands: " + e.getMessage());
        }
    }

    private void handlePipedCommands(File currentDirectory, String[] commandArgs, InputStream input, int commandIndex) throws IOException {
        while (commandIndex < commandArgs.length) {
            String currentCommand = commandArgs[commandIndex];
            switch (currentCommand) {
                case "cat":
                    if (commandIndex + 1 < commandArgs.length && commandArgs[commandIndex + 1].equals(">>")) {
                        new AppendRedirectCatCommand().execute(currentDirectory, commandArgs, input);
                        return;
                    } else if (commandIndex + 1 < commandArgs.length && commandArgs[commandIndex + 1].equals(">")) {
                        new RedirectCatCommand().execute(currentDirectory, commandArgs, input);
                        return;
                    } else {
                        System.out.println("Invalid usage of cat with pipe.");
                        return;
                    }

                case "sort":
                    ByteArrayOutputStream sortedOutput = new ByteArrayOutputStream();
                    new SortCommand().execute(input);
                    input = new ByteArrayInputStream(sortedOutput.toByteArray());
                    break;

                default:
                    System.out.println("Unknown command in pipe sequence: " + currentCommand);
                    return;
            }
            commandIndex++;
        }
    }
}
