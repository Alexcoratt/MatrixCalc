package resources.commands;

import resources.Parser;
import resources.data_types.Node;
import resources.exceptions.InvalidFlagSetException;
import resources.exceptions.ParserException;

public class Calculate extends Command{

    public Calculate(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "calc";
        description = "Вычисление значения выражения";
        syntaxTip = "calc [-v <имя переменной> (с записью в переменную)]";
        validFlagSets = new String[]{"v"};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            System.out.println("Введите выражение, испульзуя знаки (+, -, *, /, ^), вещественные числа и переменные");
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
