package resources;

import resources.commands.Command;
import resources.commands.Exit;
import resources.commands.Help;
import resources.exceptions.CommandNotFoundException;

import java.util.Arrays;
import java.util.List;

public class Parser {
    Command[] commandList = {new Help(this), new Exit(this)};

    public Parser(){
    }

    public Command find(String name) throws CommandNotFoundException {
        for (Command command : commandList) {
            if (command.name.equals(name))
                return command;
        }
        throw new CommandNotFoundException();
    }

    public void parseCommand(String com){
        String[] parts = com.split(" ");
        String comName = parts[0];
        String[] flags, args;

    }
}

/*
* Список необходимых команд:
* 1. Помощь (help)
* 2. Выход (exit)
* 3. Создать пустую матрицу
*    (mkmx [-f (с заполнением) | -i (единичная) | -r (случайные числа от нуля до единицы) | -z по-умолчанию (нулевая)]  <название переменной с матрицей> <высота матрицы> <ширина матрицы>)
* 4. Заполнение пустой матрицы
*    (flmx [-i (единичная) | -r (случайные числа от нуля до единицы) | -z (нулевая)] <название переменной>)
* 5. 
* */
