package commands;

public class PwdCommand {
    public void execute() {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
    }
}
