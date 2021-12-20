package resources.commands;

import resources.Parser;
import resources.exceptions.InvalidFlagSetException;
import resources.exceptions.ParserException;

public class VariableOut extends Command{
    public VariableOut(Parser parser) {
        super(parser);
    }

    public void init(){
        name = "varout";
        description = "Вывод значения переменной";
        syntaxTip = "varout <название переменной>";
        validFlagSets = new String[]{""};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            try {
                System.out.print(getVar(args[0]).toString());
            } catch (NullPointerException e){
                System.out.println("ОШИБКА! Переменная не найдена");
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
