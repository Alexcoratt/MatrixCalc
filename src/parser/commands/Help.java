package parser.commands;

import parser.Parser;
import exceptions.InvalidFlagSetException;
import exceptions.ParserException;

public class Help extends Command{
    public Help(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "help";
        description = "Вывод описания функций";
        syntaxTip = "help [-s (синтаксис)] [-d (описание)] [-f по-умолчанию (полное описание)] [<названия команд>]";
        validFlagSets = new String[]{"s", "d", "f"};
    }

    @Override
    public void function(char[] flags, String[] args) throws ParserException {
        if (isValidFlagSet(flags)){
            Command[] requiredCommands;
            if (args.length == 0){
                requiredCommands = parser.commandList;
            }
            else {
                requiredCommands = new Command[args.length];
                for (int i = 0; i < args.length; i++){
                    requiredCommands[i] = parser.find(args[i]);
                }
            }
            for (Command requiredCommand : requiredCommands) {
                if (flags.length == 0 || flags[0] == 'f') {
                    System.out.println(requiredCommand.name + "\t" + requiredCommand.description);
                    System.out.println(requiredCommand.getSyntaxTip());
                    System.out.println();
                }
                else if (flags[0] == 's') {
                    System.out.println(requiredCommand.name);
                    System.out.println(requiredCommand.getSyntaxTip());
                    System.out.println();
                }
                else if (flags[0] == 'd'){
                    System.out.println(requiredCommand.name + "\t" + requiredCommand.description);
                    System.out.println();
                }
            }
        }
        else
            throw new InvalidFlagSetException();
    }
}
