package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortCommand {
    public void execute(InputStream input) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            Collections.sort(lines);
            for (String sortedLine : lines) {
                System.out.println(sortedLine);
            }
        } catch (IOException e) {
            System.out.println("Error during sorting: " + e.getMessage());
        }
    }
}
