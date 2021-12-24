package commands;

import parser.Parser;
import exceptions.InvalidFlagSetException;
import exceptions.ParserException;
import exceptions.TooFewArgumentsException;

public class Unset extends Command{
    public Unset(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "unset";
        description = "Удаление переменных";
        syntaxTip = "unset <имена переменных>";
        validFlagSets = new String[]{""};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            if (args.length == 0)
                throw new TooFewArgumentsException();
            Object[] vars = new Object[args.length];
            for (int i = 0; i < args.length; i++){
                vars[i] = parser.getVar(args[i]);
            }
            for (int i = 0; i < args.length; i++){
                vars[i] = null;
                parser.variables.remove(args[i]);
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
