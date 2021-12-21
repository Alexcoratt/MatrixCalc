package resources.commands;

import resources.Parser;
import resources.data_types.Matrix;
import resources.data_types.Value;
import resources.exceptions.InvalidFlagSetException;
import resources.exceptions.ParserException;

public class FillMatrix extends Command{
    public FillMatrix(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "flmx";
        description = "Заполнение пустой матрицы";
        syntaxTip = "flmx [-i (единичная) | -r (случайные числа от нуля до единицы) | -z (нулевая) | -m по умолчанию (вручную)] <название переменной>";
        validFlagSets = new String[]{"i", "r", "z", "m"};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            Matrix mx = getVar(args[0]);
            if (flags.length == 0 || flags[0] == 'm'){
                System.out.println("Введите значения матрицы (отделяя элементы строки пробелом или табуляцией, а столбцы - переносом строки)");
                System.out.println("Строк: " + mx.height);
                System.out.println("Столбцов: " + mx.width);
                int i, j;
                String[] parts;
                for (i = 0; i < mx.height; i++){
                    parts = parser.scanner.nextLine().split(" "); // поработать над разрезкой табуляции
                    for (j = 0; j < mx.width; j++){                     // напиши свой split
                        mx.setValue(i, j, new Value(parts[j]));
                    }
                }
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
