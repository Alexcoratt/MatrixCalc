package parser.commands;

import parser.Parser;
import data_types.Matrix;
import data_types.Value;
import exceptions.InvalidFlagSetException;
import exceptions.ParserException;

public class FillMatrix extends Command{
    public FillMatrix(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "flmx";
        description = "Заполнение пустой матрицы";
        syntaxTip = "flmx [-i (единичная) | -r (случайные числа от нуля до единицы) | -z (нулевая) | -m по умолчанию (вручную)] <имя переменной>";
        validFlagSets = new String[]{"i", "r", "z", "m"};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            Matrix mx = (Matrix) parser.getVar(args[0]);
            if (flags.length == 0 || flags[0] == 'm'){
                System.out.println("Введите значения матрицы (отделяя элементы строк пробелом или табуляцией, а столбцы - переносом строки)");
                System.out.println("Строк: " + mx.height);
                System.out.println("Столбцов: " + mx.width);
                int i, j;
                String[] parts;
                Value[][] values = new Value[mx.height][mx.width];
                try {
                    for (i = 0; i < mx.height; i++) {
                        parts = parser.split(parser.scanner.nextLine(), " ", "\t");
                        if (parts.length != mx.width)
                            throw new ArrayIndexOutOfBoundsException();
                        for (j = 0; j < mx.width; j++) {
                            values[i][j] = new Value(parts[j]);
                        }
                    }
                    for (i = 0; i < mx.height; i++){
                        for (j = 0; j < mx.width; j++){
                            mx.setValue(i, j, values[i][j]);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("ОШИБКА! Неверное количество элементов в строке");
                }
            }
            else if (flags[0] == 'i'){
                mx.identify();
            }
            else if (flags[0] == 'z'){
                mx.makeZero();
            }
            else if (flags[0] == 'r'){
                mx.randomize();
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
