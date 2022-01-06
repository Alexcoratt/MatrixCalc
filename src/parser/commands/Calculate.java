package parser.commands;

import parser.Parser;
import data_types.Node;
import exceptions.InvalidFlagSetException;
import exceptions.ParserException;

public class Calculate extends Command{

    public Calculate(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "calc";
        description = "Вычисление значения выражения";
        syntaxTip = """
                calc [-v <имя переменной> (с записью в переменную)]
                Арифметические операции:
                +  сложение
                -  вычитание
                *  умножение
                /  деление
                ^  возведение в степень
                det(<имя переменной>)  определитель матрицы""";
        validFlagSets = new String[]{"v"};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            System.out.println("Введите выражение");
            String expression = parser.scanner.nextLine();
            Node nd = new Node(parser, expression);
            Object result = nd.calc();
            System.out.println(result);
            if (flags[0] == 'v'){
                parser.addVar(args[0], result);
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
