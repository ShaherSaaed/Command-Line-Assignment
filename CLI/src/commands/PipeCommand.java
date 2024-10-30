package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class PipeCommand {
    private static final List<String> noPipeCommands = Arrays.asList("mv", "cp","touch");

    public void execute(String[] commandArgs) {
        if (commandArgs.length < 3 || !commandArgs[1].equals("|")) {
            System.out.println("Usage: <command1> | <command2>");
            return;
        }

        String command1 = commandArgs[0];
        String command2 = commandArgs[2];
        String[] command2Args = Arrays.copyOfRange(commandArgs, 2, commandArgs.length);
        if (noPipeCommands.contains(command2)) {
            System.out.println("Error: The '" + command2 + "' command does not accept piped input.");
            return;
        }

        try {
            ProcessBuilder builder1 = new ProcessBuilder(command1);
            Process process1 = builder1.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process1.getInputStream()));
            ProcessBuilder builder2 = new ProcessBuilder(command2Args);
            Process process2 = builder2.start();
            OutputStream outputStream = process2.getOutputStream();
            String line;
            while ((line = reader.readLine()) != null) {
                outputStream.write((line + "\n").getBytes());
            }
            outputStream.close();
            BufferedReader finalReader = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            while ((line = finalReader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            finalReader.close();
        } catch (IOException e) {
            System.out.println("Error executing piped commands: " + e.getMessage());
        }
    }
}
