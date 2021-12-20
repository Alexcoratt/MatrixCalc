package resources;

import resources.commands.Command;
import resources.commands.Exit;
import resources.commands.Help;
import resources.exceptions.CommandNotFoundException;
import resources.exceptions.ParserExitException;

import java.util.*;

public class Parser {
    Command[] commandList = {new Command(this), new Help(this), new Exit(this)};
    Scanner scanner = new Scanner(System.in);

    public Parser(){
    }

    public Command find(String name) throws CommandNotFoundException {
        for (Command command : commandList) {
            if (command.name.equals(name))
                return command;
        }
        throw new CommandNotFoundException();
    }

    public void loop(){
        try{
            while (true){
                try {
                    parseCommand(scanner.nextLine());
                } catch (CommandNotFoundException e){
                    System.out.println("ОШИБКА! Команда не найдена");
                }
            }
        } catch (ParserExitException e){
            System.out.println("Парсер завершил работу");
        }
    }

    public void parseCommand(String com) throws CommandNotFoundException, ParserExitException {
        String[] parts = com.split(" ");
        String comName = parts[0];
        HashMap<String, String> flags = new HashMap<String, String>();
        ArrayList<String> argsList = new ArrayList<String>();

        for (int i = 1; i < parts.length; i++) {
            if (parts[i].charAt(0) == '-') {
                try {
                    flags.put(String.valueOf(parts[i].charAt(1)), parts[i + 1]);
                } catch (ArrayIndexOutOfBoundsException e){
                    flags.put(String.valueOf(parts[i].charAt(1)), "");
                }
            }
            else if (parts[i - 1].charAt(0) != '-') {
                argsList.add(parts[i]);
            }
        }
        String[] args = argsList.toArray(new String[0]);
        find(comName).function(flags, args);
    }
}

/*
* Список необходимых команд:
* 1. Помощь
*    (help [-s (синтаксис)] [-d по-умолчанию (описание)] [-f (полное описание)] <названия команд>)
* 2. Выход
*    (exit)
* 3. Создание пустой матрицы
*    (mkmx [-f (с заполнением) | -i (единичная) | -r (случайные числа от нуля до единицы) | -z по-умолчанию (нулевая)]  <название переменной с матрицей> <высота матрицы> <ширина матрицы>)
* 4. Заполнение пустой матрицы
*    (flmx [-i (единичная) | -r (случайные числа от нуля до единицы) | -z (нулевая)] <название переменной>)
* 5. Вычисление значения выражения
*    (calc [-v <название переменной> (с записью в переменную)] <выражение из переменных и чисел>)
* 6. Создание пустой числовой переменной
*    (mkvar [-f (с заполнением) <числовое значение> | -z по-умолчанию (присвоить 0)] <название переменной>)
* 7. Запись значения в переменную
*    (setvar [-z (присвоить 0)] <название переменной> <числовое значение>)
* */
