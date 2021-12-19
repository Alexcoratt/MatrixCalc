package resources.commands;

import resources.Parser;

public class Help extends Command{
    public Help(Parser parser) {
        super(parser);
    }

    @Override
    public void init() {
        name = "help";
        description = "Вывод описания функций";
        syntaxTip = "help [-s|-f] [<command_name>]";
    }
}
