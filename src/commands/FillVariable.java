package commands;

import parser.Parser;
import data_types.Value;
import exceptions.*;

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
                System.out.println("Введите значение переменной (вещественное число)");
                value.setNumber(Double.parseDouble(parser.scanner.nextLine()));
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
