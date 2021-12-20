package resources.commands;

import resources.Parser;
import resources.exceptions.ParserExitException;

import java.util.HashMap;

public class Exit extends Command{
    public Exit(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "exit";
        description = "Завершение работы программы";
        syntaxTip = "exit";
    }

    @Override
    public void function(HashMap<String, String> flags, String[] args) throws ParserExitException {
        throw new ParserExitException();
    }
}
