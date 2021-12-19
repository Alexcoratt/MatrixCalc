package resources.commands;

import resources.Parser;
import resources.exceptions.ParserExitException;

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
    public void function(String flags, String arg) throws ParserExitException {
        throw new ParserExitException();
    }
}
