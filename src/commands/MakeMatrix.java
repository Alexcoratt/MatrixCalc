package commands;

import parser.Parser;
import data_types.Matrix;
import exceptions.*;

public class MakeMatrix extends Command{
    public MakeMatrix(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "mkmx";
        description = "Создание пустой матрицы";
        syntaxTip = "mkmx [-f (с заполнением) | -i (единичная) | -r (случайные числа от нуля до единицы) | -z по-умолчанию (нулевая)]  <имя переменной с матрицей> <высота матрицы> <ширина матрицы>";
        validFlagSets = new String[]{"f", "i", "r", "z"};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            if (args.length > 3)
                throw new TooManyArgumentsException();
            try {
                String varName = args[0];
                int mxHeight = Integer.parseInt(args[1]), mxWidth = Integer.parseInt(args[2]);
                Matrix mx = new Matrix(mxHeight, mxWidth);
                parser.addVar(varName, mx);

                if (flags.length != 0) {
                    if (flags[0] == 'i') {
                        mx.identify();
                    } else if (flags[0] == 'f') {
                        parser.parseCommand("flmx -m " + varName);
                    } else if (flags[0] == 'r') {
                        mx.randomize();
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("ОШИБКА! Неверный порядок аргументов");
            } catch (ArrayIndexOutOfBoundsException e){
                throw new TooFewArgumentsException();
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
