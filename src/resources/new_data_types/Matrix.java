package resources.new_data_types;

import resources.exceptions.DifferentSizeMatrixesException;
import resources.exceptions.UnacceptableSizeMatrixException;

public class Matrix {
    public int width, height;
    public Vector[] rows, cols; // массивы строк и столбцов матрицы (содержат совпадающие объекты Value)
                                // прямая и транспонированная матрицы, иными словами

    public Matrix(int height, int width){
        this.width = width;
        this.height = height;

        rows = new Vector[height];
        int i, j;
        for (i = 0; i < height; i++){
            rows[i] = new Vector(width);
            for (j = 0; j < width; j++){
                rows[i].setValue(j, new Value());
            }
        }

        cols = new Vector[width];
        for (j = 0; j < width; j++){
            cols[j] = new Vector(height);
            for (i = 0; i < height; i++){
                cols[j].setValue(i, getValue(i, j));
            }
        }
    }

    public Matrix(Value[][] mx){
        width = mx.length;
        height = mx[0].length;

        rows = new Vector[height];
        int i, j;
        for (i = 0; i < height; i++){
            rows[i] = new Vector(width);
            for (j = 0; j < width; j++){
                rows[i].setValue(j, mx[i][j]);
            }
        }

        cols = new Vector[width];
        for (j = 0; j < width; j++){
            cols[j] = new Vector(height);
            for (i = 0; i < height; i++){
                cols[j].setValue(i, getValue(i, j));
            }
        }
    }


    // методы сложения
    public Matrix sum(Matrix other) throws DifferentSizeMatrixesException {
        if (height != other.height || width != other.width)
            throw new DifferentSizeMatrixesException();
        Matrix result = new Matrix(height, width);
        for (int i = 0; i < height; i++){
            result.getRow(i).increase(other.getRow(i));
        }
        return result;
    }

    public Matrix sum(Value other){
        Matrix result = new Matrix(height, width);
        for (int i = 0; i < height; i++){
            result.getRow(i).increase(other);
        }
        return result;
    }

    public void increase(Matrix other) throws DifferentSizeMatrixesException {
        if (height != other.height || width != other.width)
            throw new DifferentSizeMatrixesException();
        for (int i = 0; i < height; i++){
            getRow(i).increase(other.getRow(i));
        }
    }

    public void increase(Value other){
        for (int i = 0; i < height; i++){
            getRow(i).increase(other);
        }
    }


    // методы вычитания
    public Matrix subtract(Matrix other) throws DifferentSizeMatrixesException {
        if (height != other.height || width != other.width)
            throw new DifferentSizeMatrixesException();
        Matrix result = new Matrix(height, width);
        for (int i = 0; i < height; i++){
            result.getRow(i).decrease(other.getRow(i));
        }
        return result;
    }

    public Matrix subtract(Value other) throws DifferentSizeMatrixesException {
        Matrix result = new Matrix(height, width);
        for (int i = 0; i < height; i++){
            result.getRow(i).decrease(other);
        }
        return result;
    }

    public void decrease(Matrix other) throws DifferentSizeMatrixesException {
        if (height != other.height || width != other.width)
            throw new DifferentSizeMatrixesException();
        for (int i = 0; i < height; i++){
            getRow(i).decrease(other.getRow(i));
        }
    }

    public void decrease(Value other){
        for (int i = 0; i < height; i++){
            getRow(i).decrease(other);
        }
    }


    // методы умножения
    public Matrix multiply(Matrix other) throws UnacceptableSizeMatrixException {
        if (width != other.height || height != other.width)
            throw new UnacceptableSizeMatrixException();
        Matrix result = new Matrix(height, other.width);
        int i, j;
        for (i = 0; i < height; i++){
            for (j = 0; j < other.width; j++){
                result.setValue(i, j, getRow(i).multiply(other.getCol(j)));
            }
        }
        return result;
    }

    public Matrix multiply(Value other){
        Matrix result = new Matrix(height, width);
        int i, j;
        for (i = 0; i < height; i++){
            for (j = 0; j < width; j++){
                result.getValue(i, j).repeat(other);
            }
        }
        return result;
    }

    public void repeat(Matrix other){
        if (width != other.height || height != other.width)
            throw new UnacceptableSizeMatrixException();
        int i, j;
        for (i = 0; i < height; i++){
            for (j = 0; j < other.width; j++){
                setValue(i, j, getRow(i).multiply(other.getCol(j)));
            }
        }
    }

    public void repeat(Value other){
        int i, j;
        for (i = 0; i < height; i++){
            for (j = 0; j < width; j++){
                getValue(i, j).repeat(other);
            }
        }
    }


    // перевод в String
    @Override
    public String toString(){
        return toString("\n");
    }

    public String toString(String divider){
        String result[] = new String[height];
        for (int i = 0; i < height; i++){
            result[i] = rows[i].toString();
        }
        return String.join(divider, result);
    }


    // используется для отладки
    public String colsToString(){
        String result[] = new String[width];
        for (int i = 0; i < width; i++){
            result[i] = cols[i].toString();
        }
        return String.join("\n", result);
    }


    // получение данных
    public Value getValue(int row, int col){
        return rows[row].getValue(col);
    }

    public void setValue(int row, int col, Value val){
        rows[row].setValue(col, val);
    }

    public Vector getRow(int row){
        return rows[row];
    }

    public Vector getCol(int col){
        return cols[col];
    }
}
