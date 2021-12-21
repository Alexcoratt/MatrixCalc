package resources.commands;

import resources.Parser;
import resources.data_types.Value;
import resources.exceptions.*;

public class FillVariable extends Command{
    public FillVariable(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "flvar";
        description = "Запись значения в переменную";
        syntaxTip = "flvar <имя переменной>";
        validFlagSets = new String[]{""};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            if (args.length > 1)
                throw new TooManyArgumentsException();
            else if (args.length == 0)
                throw new TooFewArgumentsException();
            else {
                Value value = (Value) parser.getVar(args[0]);
                if (value == null)
                    throw new VariableDoesNotExistException();
                System.out.println("Введите значение переменной (вещественное число)");
                value.setNumber(Double.parseDouble(parser.scanner.nextLine()));
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
