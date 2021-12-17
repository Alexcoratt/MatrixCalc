package resources.new_data_types;
import resources.exceptions.*;

public class Vector {
    public int length;
    private Value values[];

    public Vector(int length){
        this.length = length;
        values = new Value[length];
    }

    public Vector(Value[] values) {
        this.values = values;
        length = values.length;
    }

    public Value getValue(int index){
        return values[index];
    }

    public void setValue(int index, Value value){
        values[index] = value;
    }


    // методы сложения
    public Vector sum(Vector other) throws DifferentVectorSizeException {
        if (length != other.length){
            throw new DifferentVectorSizeException();
        }
        Vector result = new Vector(length);
        for (int i = 0; i < length; i++){
            result.setValue(i, getValue(i).sum(other.getValue(i)));
        }
        return result;
    }

    public Vector sum(Value other){
        Vector result = new Vector(length);
        for (int i = 0; i < length; i++){
            result.setValue(i, getValue(i).sum(other));
        }
        return result;
    }

    public void increase(Vector other){
        for (int i = 0; i < length; i++){
            getValue(i).increase(other.getValue(i));
        }
    }

    public void increase(Value other){
        for (int i = 0; i < length; i++){
            getValue(i).increase(other);
        }
    }


    // методы вычитания
    public Vector subtract(Vector other) throws DifferentVectorSizeException {
        if (length != other.length){
            throw new DifferentVectorSizeException();
        }
        Vector result = new Vector(length);
        for (int i = 0; i < length; i++){
            result.setValue(i, getValue(i).subtract(other.getValue(i)));
        }
        return result;
    }

    public Vector subtract(Value other){
        Vector result = new Vector(length);
        for (int i = 0; i < length; i++){
            result.setValue(i, getValue(i).subtract(other));
        }
        return result;
    }

    public void decrease(Vector other){
        for (int i = 0; i < length; i++){
            getValue(i).decrease(other.getValue(i));
        }
    }

    public void decrease(Value other){
        for (int i = 0; i < length; i++){
            getValue(i).decrease(other);
        }
    }


    // методы умножения
    public Value multiply(Vector other) throws DifferentVectorSizeException{
        if (length != other.length)
            throw new DifferentVectorSizeException();
        Value result = new Value();
        for (int i = 0; i < length; i++){
            result.increase(getValue(i).multiply(other.getValue(i)));
        }
        return result;
    }

    public Vector multiply(Value other){
        Vector result = new Vector(length);
        for (int i = 0; i < length; i++){
            result.setValue(i, getValue(i).multiply(other));
        }
        return result;
    }

    public void repeat(Value other){
        for (int i = 0; i < length; i++){
            getValue(i).repeat(other);
        }
    }


    // методы деления
    public Value divide(Vector other) throws DifferentVectorSizeException{
        if (length != other.length)
            throw new DifferentVectorSizeException();
        Value result = new Value();
        for (int i = 0; i < length; i++){
            result.increase(getValue(i).divide(other.getValue(i)));
        }
        return result;
    }

    public Vector divide(Value other){
        Vector result = new Vector(length);
        for (int i = 0; i < length; i++){
            result.setValue(i, getValue(i).divide(other));
        }
        return result;
    }

    public void reduce(Value other){
        for (int i = 0; i < length; i++){
            getValue(i).reduce(other);
        }
    }


    // перевод в String
    @Override
    public String toString(){
        return toString("\t");
    }

    public String toString(String divider){
        String strValues[] = new String[length];
        for (int i = 0; i < length; i++){
            strValues[i] = getValue(i).toString();
        }
        return String.join(divider, strValues);
    }
}
