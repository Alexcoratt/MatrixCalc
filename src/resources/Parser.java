package resources;

import java.util.Arrays;
import java.util.List;

public class Parser {
    public Parser(){
    }

    public void parseCommand(String command){
        String[] parts = command.split(" ");
        String commandName = parts[0];
        System.out.println(Arrays.toString(divideCommand(parts)));
    }

    static String[] divideCommand(String[] parts){
        byte i = 0;
        while (i < parts.length && parts[i].charAt(0) == '-'){
            i++;
        }
        String[] options = new String[i];
        System.arraycopy(sortedParts(parts), 0, options, 0, i);
        return options;
    }

    static String[] sortedParts(String[] parts){
        byte i, j;
        String change;
        for (i = 0; i < parts.length; i++){
            for (j = i; j < parts.length - 1; j++){
                if (parts[j].charAt(0) > parts[j + 1].charAt(0)){
                    change = parts[j];
                    parts[j] = parts[j + 1];
                    parts[j + 1] = change;
                }
            }
        }
        return parts;
    }
}
