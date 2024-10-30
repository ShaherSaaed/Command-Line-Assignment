package commands;

public class HelpCommand {
    public void execute() {
        System.out.println("Available Commands:");
        System.out.println("pwd         - Prints the current working directory.");
        System.out.println("cd <dir>    - Changes the current directory to <dir>.");
        System.out.println("ls          - Lists files and directories in the current directory.");
        System.out.println("ls -a       - Lists all files, including hidden files.");
        System.out.println("ls -r       - Lists files and directories in reverse order.");
        System.out.println("mkdir <dir> - Creates a new directory named <dir>.");
        System.out.println("rmdir <dir> - Removes an empty directory named <dir>.");
        System.out.println("touch <file>- Creates a new empty file named <file>.");
        System.out.println("mv <src> <dest> - Moves or renames a file or directory.");
        System.out.println("rm <file>   - Removes a file.");
        System.out.println("cat <file>  - Displays the content of a file.");
        System.out.println("> <file>    - Redirects output to a file, overwriting it.");
        System.out.println(">> <file>   - Redirects output to a file, appending to it.");
        System.out.println("|           - Pipes output of one command to another.");
        System.out.println("exit        - Exits the CLI.");
        System.out.println("help        - Displays this help message.");
    }
}
