package resources.commands;

import resources.Parser;
import resources.exceptions.ParserExitException;

import java.util.Arrays;
import java.util.HashMap;

public class Command {
    public String name, description, syntaxTip;
    public Parser parser;

    public Command(Parser parser){
        this.parser = parser;
        init();
    }

    public void init(){
        name = "cmd";
        description = "Базовая команда";
        syntaxTip = "cmd/nФлагов нет";
    }

    public void function(HashMap<String, String> flags, String[] args) throws ParserExitException {
        for (String flag : flags.keySet()){
            System.out.println(flag + " " + flags.get(flag));
        }
        System.out.println(Arrays.toString(args));
    }
}
