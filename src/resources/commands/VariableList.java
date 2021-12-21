package resources.commands;

import resources.Parser;
import resources.exceptions.InvalidFlagSetException;
import resources.exceptions.ParserException;
import resources.exceptions.TooManyArgumentsException;

public class VariableList extends Command{
    public VariableList(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "varlist";
        description = "Вывод списка используемых переменных на экран";
        syntaxTip = "varlist [-t (с описанием типа данных)] [-v (с выводом значения)]";
        validFlagSets = new String[]{"tv"};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            if (args.length > 0)
                throw new TooManyArgumentsException();
            for (String varName : parser.variables.keySet()){
                System.out.print(varName);
                if (isSubChar('t', flags))
                    System.out.print("\t" + parser.getVar(varName).getClass());
                if (isSubChar('v', flags))
                    System.out.print("\n" + parser.getVar(varName).toString());
                System.out.println();
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
