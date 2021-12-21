package resources.commands;

import resources.Parser;
import resources.exceptions.InvalidFlagSetException;
import resources.exceptions.ParserException;
import resources.exceptions.VariableDoesNotExistException;

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
            if (variable == null)
                throw new VariableDoesNotExistException();
            System.out.println(variable);
        }
        else
            throw new InvalidFlagSetException();
    }
}
