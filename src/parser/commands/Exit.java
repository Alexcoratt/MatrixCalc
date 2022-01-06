package parser.commands;

import parser.Parser;
import exceptions.InvalidFlagSetException;
import exceptions.ParserException;
import exceptions.ParserExitException;

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
