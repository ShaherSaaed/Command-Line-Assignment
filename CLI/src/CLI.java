import commands.*;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class CLI {
    private static File currentDirectory = new File(System.getProperty("user.dir"));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] inputParts = input.split(" ");
            String command = inputParts[0];
            String[] commandArgs = Arrays.copyOfRange(inputParts, 1, inputParts.length);

            switch (command) {
                case "pwd":
                    new PwdCommand().execute(currentDirectory);
                    break;
                case "cd":
                    CdCommand cdCommand = new CdCommand();
                    cdCommand.execute(commandArgs);
                    currentDirectory = cdCommand.getCurrentDirectory();
                    break;
                case "ls":
                    if (inputParts.length == 2) {
                        switch (inputParts[1]) {
                            case "-a":
                                new LsACommand().execute(currentDirectory, commandArgs);
                                break;
                            case "-r":
                                new LsRCommand().execute(currentDirectory, commandArgs);
                                break;
                            default:
                                System.out.println("Unknown command: " + command + ' ' + inputParts[1]);
                                break;
                        }
                    } else if (inputParts.length == 3) { //ls >> <file>
                        switch (inputParts[1]) {
                            case ">>":
                                new AppendRedirectLsCommand().execute(currentDirectory, commandArgs);
                                break;
                            case ">":
                                new RedirectLsCommand().execute(currentDirectory, commandArgs);
                                break;
                        }

                    } else if (inputParts.length == 4) { // ls -<argument> >> <file>
                        switch (inputParts[1]) {
                            case "-a":
                                if (inputParts[2].equals(">>")) {
                                    new AppendRedirectLsACommand().execute(currentDirectory, commandArgs);
                                } else if (inputParts[2].equals(">")) {
                                    new RedirectLsACommand().execute(currentDirectory, commandArgs);
                                }
                                break;
                            case "-r":
                                if (inputParts[2].equals(">>")) {
                                    new AppendRedirectLsRCommand().execute(currentDirectory, commandArgs);
                                } else if (inputParts[2].equals(">")) {
                                    new RedirectLsRCommand().execute(currentDirectory, commandArgs);
                                }
                                break;
                        }

                    } else if (inputParts.length == 1) { // ls
                        new LsCommand().execute(currentDirectory, commandArgs);
                    } else {
                        System.out.println("Unknown command");
                    }
                    break;
                case "mkdir":
                    new MkdirCommand().execute(currentDirectory, commandArgs);
                    break;
                case "rmdir":
                    new RmdirCommand().execute(currentDirectory, commandArgs);
                    break;
                case "touch":
                    new TouchCommand().execute(currentDirectory, commandArgs);
                    break;
                case "mv":
                    new MvCommand().execute(commandArgs);
                    break;
                case "rm":
                    new RmCommand().execute(currentDirectory, commandArgs);
                    break;
                case "cat":
                    if (inputParts.length == 4) {
                        switch (inputParts[2]) {
                            case ">>":
                                new AppendRedirectCatCommand().execute(currentDirectory, commandArgs);
                                break;
                            case ">":
                                new RedirectCatCommand().execute(currentDirectory, commandArgs);
                                break;
                        }
                    } else if (inputParts.length == 3) {
                        if (inputParts[0].equals("cat")) {
                            switch (inputParts[1]) {
                                case ">>":
                                    new AppendRedirectCatCommand().execute(currentDirectory, commandArgs);
                                    break;
                                case ">":
                                    new RedirectCatCommand().execute(currentDirectory, commandArgs);
                                    break;
                            }
                        }
                    } else {
                        new CatCommand().execute(currentDirectory, commandArgs);
                        break;
                    }
//                case ">":
//                    new RedirectCommand().execute(commandArgs);
//                    break;
//                case ">>":
//                    new AppendRedirectCommand().execute(commandArgs);
                    break;
                case "|":
                    new PipeCommand().execute(commandArgs);
                    break;
                case "exit":
                    running = false;
                    break;
                case "help":
                    new HelpCommand().execute();
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        }
        scanner.close();
    }
}