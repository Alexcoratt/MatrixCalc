package resources.commands;

import resources.Parser;
import resources.data_types.Matrix;
import resources.exceptions.InvalidFlagSetException;
import resources.exceptions.ParserException;

public class MakeMatrix extends Command{
    public MakeMatrix(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "mkmx";
        description = "Создание пустой матрицы";
        syntaxTip = "mkmx [-f (с заполнением) | -i (единичная) | -r (случайные числа от нуля до единицы) | -z по-умолчанию (нулевая)]  <название переменной с матрицей> <высота матрицы> <ширина матрицы>";
        validFlagSets = new String[]{"f", "i", "r", "z"};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            try {
                String varName = args[0];
                int mxHeight = Integer.parseInt(args[1]), mxWidth = Integer.parseInt(args[2]);
                parser.variables.put(varName, new Matrix(mxHeight, mxWidth));

                if (flags.length != 0) {
                    if (flags[0] == 'i') {
                        getVar(varName).identify();
                    } else if (flags[0] == 'f') {
                        System.out.println("Не готово");
                    } else if (flags[0] == 'r') {
                        getVar(varName).randomize();
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("ОШИБКА! Неверный порядок аргументов");
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
