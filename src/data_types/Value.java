package data_types;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Value {
    public BigDecimal number;

    public Value(double num){
        number = BigDecimal.valueOf(num);
    }

    public Value(BigDecimal num){
        number = num;
    }

    public Value(){
        number = BigDecimal.valueOf(0.0);
    }

    public Value(String strNum){
        number = BigDecimal.valueOf(Double.parseDouble(strNum));
    }

    public Value getClone(){
        return new Value(number);
    }

    public void setNumber(double num) {
        number = BigDecimal.valueOf(num);
    }


    // методы суммирования
    public Value sum(Value other){
        return new Value(number.add(other.number));
    }

    public void increase(Value other){
        number = number.add(other.number);
    }


    // методы вычитания
    public Value subtract(Value other){
        return new Value(number.subtract(other.number));
    }

    public void decrease(Value other){
        number = number.subtract(other.number);
    }


    // методы умножения
    public Value multiply(Value other){
        return new Value(number.multiply(other.number));
    }

    public void repeat(Value other){
        number = number.multiply(other.number);
    }

    public Value multiply(int other){
        return multiply(new Value(other));
    }

    public void repeat(int other){
        repeat(new Value(other));
    }

    public Value power(Value other) {
        return new Value(number.pow(other.toInt()));
    }


    // методы деления
    public Value divide(Value other){
        return new Value(number.divide(other.number, 8, RoundingMode.HALF_UP));
    }

    public void reduce(Value other){
        number = number.divide(other.number, 8, RoundingMode.HALF_UP);
    }


    // методы сравнения
    public int compare(Value other){
        return number.compareTo(other.number);
    }


    // перевод в String
    @Override
    public String toString(){
        return String.valueOf(number);
    }

    public String toString(int strSpace){
        String output = String.valueOf(number);
        return output + strMul(" ", strSpace - output.length());
    }


    // перевод в int
    public int toInt(){
        return number.intValue();
    }

    public static String strMul(String str, int num) {
        StringBuilder result = new StringBuilder();
        while (num > 0){
            result.append(str);
            num--;
        }
        return result.toString();
    } // повтороение подстроки str num раз


    // установка значения
    public void setRandom(){
        number = BigDecimal.valueOf(Math.random());
    } // устанавливает случайное значение number в диапазоне [0; 1)
}
