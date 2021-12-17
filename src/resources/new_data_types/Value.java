package resources.new_data_types;

import java.math.BigDecimal;

// пока что работает с типом данных double
public class Value {
    public BigDecimal value;

    public Value(double num){
        value = new BigDecimal(num);
    }

    public Value(BigDecimal num){
        value = num;
    }

    public Value(){
        value = new BigDecimal(0);
    }


    // методы суммирования
    public Value sum(Value other){
        return new Value(value.add(other.value));
    }

    public void increase(Value other){
        value = value.add(other.value);
    }


    // методы вычитания
    public Value subtract(Value other){
        return new Value(value.subtract(other.value));
    }

    public void decrease(Value other){
        value = value.subtract(other.value);
    }


    // методы умножения
    public Value multiply(Value other){
        return new Value(value.multiply(other.value));
    }

    public void repeat(Value other){
        value = value.multiply(other.value);
    }


    // методы деления
    public Value divide(Value other){
        return new Value(value.divide(other.value));
    }

    public void reduce(Value other){
        value = value.divide(other.value);
    }

    // перевод в String
    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
