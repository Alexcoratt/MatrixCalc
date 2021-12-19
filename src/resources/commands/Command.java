package resources.commands;

import resources.Parser;
import resources.exceptions.ParserExitException;

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

    public void function(String flags, String arg) throws ParserExitException {
        System.out.println("nothing");
    }
}
