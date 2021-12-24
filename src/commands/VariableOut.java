package commands;

import parser.Parser;
import exceptions.InvalidFlagSetException;
import exceptions.ParserException;

public class VariableOut extends Command{
    public VariableOut(Parser parser) {
        super(parser);
    }

    public void init(){
        name = "varout";
        description = "Вывод значения переменной";
        syntaxTip = "varout <имя переменной>";
        validFlagSets = new String[]{""};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            Object variable = parser.getVar(args[0]);
            System.out.println(variable);
        }
        else
            throw new InvalidFlagSetException();
    }
}
