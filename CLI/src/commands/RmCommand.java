package commands;

import java.io.File;

public class RmCommand {
    public void execute(String[] args) {
        if(args.length<1){
            System.out.println("Error: Missing file name.");
            return;
        }
        File file=new File(args[0]);
        if(file.isFile()&&file.delete()){
            System.out.println("File removed: " + args[0]);
        }else{
            System.out.println("Failed to remove file: " + args[0] + " (Make sure it is empty)");
        }
    }
}
