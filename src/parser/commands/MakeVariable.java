package parser.commands;

import parser.Parser;
import data_types.Value;
import exceptions.*;

public class MakeVariable extends Command{
    public MakeVariable(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "mkvar";
        description = "Создание пустой числовой переменной";
        syntaxTip = "mkvar [-f (с заполнением вручную) | -r (присвоить случайное значение от 0 до 1) | -z по-умолчанию (присвоить 0)] <имя переменной>";
        validFlagSets = new String[]{"f", "r", "z"};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            if (args.length > 1)
                throw new TooManyArgumentsException();
            try {
                String varName = args[0];

                Value value = new Value();
                if (flags.length == 0 || flags[0] == 'z') {
                    parser.addVar(varName, value);
                } else if (flags[0] == 'r') {
                    value.setRandom();
                    parser.addVar(varName, value);
                } else if (flags[0] == 'f') {
                    System.out.println("Введите значение переменной (вещественное число)");
                    value.setNumber(Double.parseDouble(parser.scanner.nextLine()));
                    parser.addVar(varName, value);
                }
            } catch (ArrayIndexOutOfBoundsException e){
                throw new TooFewArgumentsException();
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
