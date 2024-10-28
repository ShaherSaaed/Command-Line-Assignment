import commands.*;

import java.util.Arrays;
import java.util.Scanner;

public class CLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] inputParts = input.split(" ");
            String command = inputParts[0].toLowerCase();
            String[] commandArgs = Arrays.copyOfRange(inputParts, 1, inputParts.length);

            switch (command) {
                case "pwd":
                    new PwdCommand().execute();
                    break;
                case "cd":
                    new CdCommand().execute(commandArgs);
                    break;
                case "ls":
                    new LsCommand().execute(commandArgs);
                    break;
                case "ls -a":
                    new LsACommand().execute(commandArgs);
                    break;
                case "ls -r":
                    new LsRCommand().execute(commandArgs);
                    break;
                case "mkdir":
                    new MkdirCommand().execute(commandArgs);
                    break;
                case "rmdir":
                    new RmdirCommand().execute(commandArgs);
                    break;
                case "touch":
                    new TouchCommand().execute(commandArgs);
                    break;
                case "mv":
                    new MvCommand().execute(commandArgs);
                    break;
                case "rm":
                    new RmCommand().execute(commandArgs);
                    break;
                case "cat":
                    new CatCommand().execute(commandArgs);
                    break;
                case ">":
                    new RedirectCommand().execute(commandArgs);
                    break;
                case ">>":
                    new AppendRedirectCommand().execute(commandArgs);
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