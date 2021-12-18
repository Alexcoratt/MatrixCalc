package resources.new_data_types;

import java.math.BigDecimal;

// пока что работает с типом данных double
public class Value {
    public BigDecimal number;

    public Value(double num){
        number = new BigDecimal(num);
    }

    public Value(BigDecimal num){
        number = num;
    }

    public Value(){
        number = new BigDecimal(0);
    }

    public Value getClone(){
        return new Value(number);
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


    // методы деления
    public Value divide(Value other){
        return new Value(number.divide(other.number));
    }

    public void reduce(Value other){
        number = number.divide(other.number);
    }

    // перевод в String
    @Override
    public String toString(){
        return String.valueOf(number);
    }
}
