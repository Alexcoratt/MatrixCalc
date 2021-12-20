package resources.commands;

import resources.Parser;
import resources.exceptions.InvalidFlagSetException;
import resources.exceptions.ParserException;
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
        validFlagSets = new String[]{""};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags))
            throw new ParserExitException();
        else
            throw new InvalidFlagSetException();
    }
}
