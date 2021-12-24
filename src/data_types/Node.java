package data_types;

import parser.Parser;
import arithmetics.*;
import exceptions.*;

public class Node {
    public char[] operators = new char[]{'+', '-', '*', '/', '^'}; // расставлены в порядке возрастания приоритета выполнения
    public Node left = null, right = null;
    public Object value = null;
    public Operator operator;
    public Parser parser;

    public Node(Parser prs, String expression) {
        parser = prs;
        expression = expression.trim();
        if (expression.charAt(0) == '(' && expression.charAt(expression.length() - 1) == ')')
            expression = expression.substring(1, expression.length() - 1);
        if (isContains(operators, expression))
            separate(expression);
        else{
            try {
                value = new Value(Double.parseDouble(expression));
            } catch (NumberFormatException e){
                try {
                    if (expression.startsWith("det")) {
                        expression = expression.substring(4, expression.length() - 1);
                        value = ((Matrix) parser.getVar(expression)).fastDeterminant();
                    }
                    else
                        value = parser.getVar(expression);
                } catch (VariableDoesNotExistException e1){
                    System.out.println("ОШИБКА! Переменная с именем "+ expression + " не существует");
                }
            }
        }
    }

    public Object calc() throws OperatorErrorException {
        if (value != null)
            return value;
        try {
            return operator.calc(left.calc(), right.calc());
        } catch (NullPointerException e){
            throw new WrongExpressionException();
        }
    }

    public int getPriority(char chr){
        for (int i = 0; i < operators.length; i++){
            if (chr == operators[i])
                return i;
        }
        return -1;
    }

    public boolean isContains(char[] chars, String str){
        int strLen = str.length();
        for (char chr : chars){
            for (int i = 0; i < strLen; i++){
                if (str.charAt(i) == chr)
                    return true;
            }
        }
        return false;
    }

    public void separate(String expression){
        int openedBrackets = 0;
        char currentChar;

        int expLen = expression.length();

        //System.out.println(expression);

        int currentPriority, weakestPriority = operators.length, weakestCharNum = 0;
        for (int i = expLen - 1; i >= 0; i--){
            currentChar = expression.charAt(i);
            if (currentChar == ')')
                openedBrackets++;
            else if (currentChar == '(')
                openedBrackets--;

            if (openedBrackets == 0){
                currentPriority = getPriority(currentChar);
                if (currentPriority >= 0 && currentPriority < weakestPriority){
                    weakestPriority = currentPriority;
                    weakestCharNum = i;
                }
            }
        }

        String part1 = expression.substring(0, weakestCharNum).trim();
        String part2 = expression.substring(weakestCharNum + 1, expLen).trim();
        char oper = expression.charAt(weakestCharNum);
        try {
            operator = parser.getOperator(oper);
        } catch (OperatorDoesNotExistException e) {
            System.out.println(e);
        }

        if (!part1.isEmpty())
            left = new Node(parser, part1);
        else if (oper == '-'){
            left = new Node(parser, "0");
        }
        if (!part2.isEmpty())
            right = new Node(parser, part2);
    }
}
