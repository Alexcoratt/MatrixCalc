package resources.new_data_types;

// пока что работает с типом данных double
public class Number {
    public double value;

    Number(double num){
        value = num;
    }

    Number(){
        value = 0;
    }

    public Number sum(Number other){
        return new Number(value + other.value);
    }

    public Number substraction(Number other){
        return new Number(value - other.value);
    }

    public Number mul(Number other){
        return new Number(value * other.value);
    }

    public Number div(Number other){
        return new Number(value / other.value);
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
