package resources;

import resources.commands.*;
import resources.data_types.Matrix;
import resources.exceptions.*;

import java.util.*;

public class Parser {
    public Command[] commandList = {
            new Command(this),
            new Help(this),
            new Exit(this),
            new MakeMatrix(this),
            new VariableOut(this),
            new FillMatrix(this)
    };
    public Scanner scanner = new Scanner(System.in);
    public HashMap<String, Matrix> variables = new HashMap<String, Matrix>();

    public Parser(){}

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
                    System.out.print("> ");
                    parseCommand(scanner.nextLine());
                } catch (CommandNotFoundException e){
                    System.out.println("ОШИБКА! Команда не найдена");
                } catch (InvalidFlagSetException e){
                    System.out.println("ОШИБКА! Неверный набор флагов");
                } catch (CommandErrorException e){
                    System.out.println("ОШИБКА! Ошибка команды");
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("ОШИБКА! Недостаток аргументов");
                }
            }
        } catch (ParserExitException e){
            System.out.println("Парсер завершил работу");
        } catch (ParserException e){
            System.out.println("ОШИБКА! Ошибка парсера");
        }
    }

    public void parseCommand(String com) throws ParserException {
        String[] parts = com.split(" ");
        String comName = parts[0];
        int partLen, i, j, flagsCount = 0, argsCount = 0;

        for (i = 1; i < parts.length; i++) {
            if (parts[i].charAt(0) == '-') {
                partLen = parts[i].length();
                flagsCount += partLen - 1;
            }
            else {
                argsCount++;
            }
        }

        char[] flags = new char[flagsCount];
        String[] args = new String[argsCount];
        int flagsCounter = 0, argsCounter = 0;
        for (i = 1; i < parts.length; i++) {
            if (parts[i].charAt(0) == '-') {
                partLen = parts[i].length();
                for (j = 1; j < partLen; j++)
                    flags[flagsCounter++] = parts[i].charAt(j);
            }
            else {
                args[argsCounter++] = parts[i];
            }
        }

        find(comName).function(flags, args);
    }
}

/*
* Список необходимых команд:
* 1. Помощь
*    (help [-s (синтаксис)] [-d по-умолчанию (описание)] [-f (полное описание)] [<названия команд>])
* 2. Выход
*    (exit)
* 3. Создание пустой матрицы
*    (mkmx [-f (с заполнением) | -i (единичная) | -r (случайные числа от нуля до единицы) | -z по-умолчанию (нулевая)]  <название переменной с матрицей> <высота матрицы> <ширина матрицы>)
* 4. Заполнение пустой матрицы
*    (flmx [-i (единичная) | -r (случайные числа от нуля до единицы) | -z (нулевая) | -m по умолчанию (вручную)] <название переменной>)
* 5. Вычисление значения выражения
*    (calc [-v <название переменной> (с записью в переменную)] <выражение из переменных и чисел>)
* 6. Создание пустой числовой переменной
*    (mkvar [-f (с заполнением) | -z по-умолчанию (присвоить 0)] <название переменной>)
* 7. Запись значения в переменную
*    (setvar [-z по умолчанию (присвоить 0)] <название переменной> <числовое значение>)
* 8. Вывод значения переменной
*    (varout <название переменной>)
* 9. Удаление переменной
*    (unset <название переменной>)
* */
