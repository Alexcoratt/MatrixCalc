package resources;

import resources.arithmetics.*;
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
            new FillMatrix(this),
            new MakeVariable(this),
            new FillVariable(this),
            new Unset(this),
            new VariableList(this),
            new Calculate(this)
    };

    public Operator[] operators = {
            new Operator(),
            new Sum(),
            new Subtraction(),
            new Multiplication(),
            new Division(),
            new Power()
    };

    public Scanner scanner = new Scanner(System.in);
    public HashMap<String, Object> variables = new HashMap<String, Object>();

    public Parser(){}


    // служебные методы
    public boolean isIn(Object elem, Object[] arr){
        for (Object part : arr){
            if (Objects.equals(part, elem))
                return true;
        }
        return false;
    }

    public String[] split(String str, String ...dividers){
        for (String divider : dividers){
            str = str.replace(divider, dividers[0]);
        }
        String[] parts = str.split(dividers[0]), result;
        int resLen = parts.length;
        for (String part : parts){
            if (isIn(part, dividers) || Objects.equals(part, ""))
                resLen--;
        }
        result = new String[resLen];
        int resCounter = 0;
        for (String part : parts){
            if (!isIn(part, dividers) && !Objects.equals(part, ""))
                result[resCounter++] = part;
        }
        return result;
    }


    // методы работы с командами
    public Command find(String name) throws CommandNotFoundException {
        for (Command command : commandList) {
            if (command.name.equals(name))
                return command;
        }
        throw new CommandNotFoundException();
    }


    // методы работы с операторами
    public Operator getOperator(char symbol) throws OperatorDoesNotExistException{
        for (Operator op : operators){
            if (op.symbol == symbol)
                return op;
        }
        throw new OperatorDoesNotExistException();
    }

    public Operator getOperator(){
        return operators[0];
    }


    // методы работы с переменными
    public Object getVar(String varName) throws VariableDoesNotExistException {
        Object variable = variables.get(varName);
        if (variable == null)
            throw new VariableDoesNotExistException();
        return variable;
    }

    public void addVar(String key, Object value) throws VariableHasAlreadyExistException{
        try{
            getVar(key);
            throw new VariableHasAlreadyExistException();
        } catch (VariableDoesNotExistException e) {
            variables.put(key, value);
        }
    }


    // метод запуска
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
                } catch (TooFewArgumentsException e){
                    System.out.println("ОШИБКА! Недостаток аргументов");
                } catch (TooManyArgumentsException e){
                    System.out.println("ОШИБКА! Избыток аргументов");
                } catch (VariableHasAlreadyExistException e){
                    System.out.println("ОШИБКА! Переменная с данным именем уже существует");
                } catch (VariableDoesNotExistException e){
                    System.out.println("ОШИБКА! Переменная с данным именем не существует");
                } catch (CommandErrorException e){
                    System.out.println("ОШИБКА! Ошибка команды");
                } catch (WrongExpressionException e){
                    System.out.println("ОШИБКА! Неверное написание выражения");
                } catch (UnacceptableSizeMatrixException e){
                    System.out.println("ОШИБКА! Размеры матриц не позволяют провести данную операцию");
                }
            }
        } catch (ParserExitException e){
            System.out.println("Парсер завершил работу");
        } catch (ParserException e){
            System.out.println("ОШИБКА! Ошибка парсера");
        }
    }

    public void parseCommand(String com) throws ParserException {
        String[] parts = split(com, " ", "\t");
        try {
            String comName = parts[0];
            int partLen, i, j, flagsCount = 0, argsCount = 0;

            for (i = 1; i < parts.length; i++) {
                if (parts[i].charAt(0) == '-') {
                    partLen = parts[i].length();
                    flagsCount += partLen - 1;
                } else {
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
                } else {
                    args[argsCounter++] = parts[i];
                }
            }

            find(comName).function(flags, args);
        } catch (ArrayIndexOutOfBoundsException e){}
    }
}

/*
* Список необходимых команд:
* 1. Помощь
*    (help [-s (синтаксис)] [-d по-умолчанию (описание)] [-f (полное описание)] [<названия команд>])
* 2. Выход
*    (exit)
* 3. Создание пустой матрицы
*    (mkmx [-f (с заполнением) | -i (единичная) | -r (случайные числа от нуля до единицы) | -z по-умолчанию (нулевая)]  <имя переменной с матрицей> <высота матрицы> <ширина матрицы>)
* 4. Заполнение пустой матрицы
*    (flmx [-i (единичная) | -r (случайные числа от нуля до единицы) | -z (нулевая) | -m по умолчанию (вручную)] <имя переменной>)
* 5. Вычисление значения выражения
*    (calc [-v <имя переменной> (с записью в переменную)])
* 6. Создание пустой числовой переменной
*    (mkvar [-f (с заполнением) | -z по-умолчанию (присвоить 0)] <имя переменной>)
* 7. Запись значения в переменную
*    (flvar <имя переменной> <числовое значение>)
* 8. Вывод значения переменной
*    (varout <имя переменной>)
* 9. Удаление переменной
*    (unset <имена переменных>)
* */
