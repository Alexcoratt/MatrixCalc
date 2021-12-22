package resources.data_types;

import resources.exceptions.DifferentSizeMatrixesException;
import resources.exceptions.NonSquareMatrixException;
import resources.exceptions.UnacceptableSizeMatrixException;

public class Matrix {
    public int width, height;
    public Vector[] rows, cols; // массивы строк и столбцов матрицы (содержат совпадающие объекты Value)
                                // прямая и транспонированная матрицы, иными словами

    public Matrix(int height, int width){
        this.width = width;
        this.height = height;

        init();
        int i, j;
        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++)
                setCell(i, j, new Cell());

    }

    public Matrix(Value[][] mx){
        height = mx.length;
        width = mx[0].length;

        init();
        int i, j;
        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++)
                setCell(i, j, new Cell(mx[i][j]));
    }

    // методы сложения
    public Matrix sum(Matrix other) throws DifferentSizeMatrixesException {
        if (height != other.height || width != other.width)
            throw new DifferentSizeMatrixesException();
        Matrix result = new Matrix(height, width);
        int i, j;
        for (i = 0; i < height; i++){
            for (j = 0; j < width; j++){
                result.setValue(i, j, getValue(i, j).sum(other.getValue(i, j)));
            }
        }
        return result;
    }

    public Matrix sum(Value other){
        Matrix result = new Matrix(height, width);
        int i, j;
        for (i = 0; i < height; i++){
            for (j = 0; j < width; j++){
                result.setValue(i, j, getValue(i, j).sum(other));
            }
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
        int i, j;
        for (i = 0; i < height; i++){
            for (j = 0; j < width; j++){
                result.setValue(i, j, getValue(i, j).subtract(other.getValue(i, j)));
            }
        }
        return result;
    }

    public Matrix subtract(Value other) throws DifferentSizeMatrixesException {
        Matrix result = new Matrix(height, width);
        int i, j;
        for (i = 0; i < height; i++){
            for (j = 0; j < width; j++){
                result.setValue(i, j, getValue(i, j).subtract(other));
            }
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
                result.setValue(i, j, getValue(i, j).multiply(other));
            }
        }
        return result;
    }

    public void repeat(Value other){
        int i, j;
        for (i = 0; i < height; i++){
            for (j = 0; j < width; j++){
                getValue(i, j).repeat(other);
            }
        }
    }


    // методы деления
    public Matrix divide(Matrix other){
        return multiply(other.fastOpposite());
    }

    public Matrix divide(Value other){
        Matrix result = new Matrix(height, width);
        int i, j;
        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++)
                result.setValue(i, j, getValue(i, j).divide(other));
        return result;
    }

    // методы возведения в степень
    public Matrix power(int other){
        Matrix result = getIdentity();
        Matrix multiplier = getIdentity();
        if (other < 0) {
            multiplier = fastOpposite();
            other = -1 * other;
        }
        else if (other > 0){
            multiplier = getClone();
        }

        while (other > 0) {
            result = result.multiply(multiplier);
            other--;
        }

        return result;
     }

    public Matrix power(Value other){
        return power(other.toInt());
     }


    // методы работы с матрицами
    public void transpose(){
        Vector[] tmp = rows;
        rows = cols;
        cols = tmp;

        int tmpSize = height;
        height = width;
        width = tmpSize;
    } // транспонирование текущей матрицы

    public Matrix transposed(){
        Matrix result = getClone();
        result.transpose();
        return result;
    } // получение транспонированной матрицы

    public void identify(){
        int i, j;
        for (i = 0; i < height; i++){
            for (j = 0; j < width; j++){
                if (i != j)
                    getValue(i, j).setNumber(0);
                else
                    getValue(i, j).setNumber(1);
            }
        }
    } // сделать матрицу единичной

    public void randomize(){
        int i, j;
        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++)
                getValue(i, j).setRandom();
    } // задать матрице случайные значения в диапазоне [0; 1)

    public void makeZero(){
        int i, j;
        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++)
                getValue(i, j).setNumber(0);
    }

    public Matrix getIdentity(){
        Matrix result = getClone();
        result.identify();
        return result;
    } // получить единичную матрицу

    public Matrix minorMatrix(int row, int col){
        Matrix result = new Matrix(height - 1, width - 1);
        int resI = 0, resJ = 0, i, j;
        for (i = 0; i < height; i++){
            if (i != row){
                for (j = 0; j < width; j++){
                    if (j != col) {
                        result.setCell(resI, resJ, getCell(i, j).getClone());
                        resJ++;
                    }
                }
                resI++;
            }
            resJ = 0;
        }
        return result;
    } // получение минорной матрицы (копия текущей матрицы без строки под номером row и столбца под номером col)

    // набор вычисления точного детерминанта
    // _____________________________________
    public Value minor(int row, int col){
        return minorMatrix(row, col).determinant();
    } // получение минора данной матрицы

    public Value complement(int row, int col){
        return minor(row, col).multiply((int)Math.pow(-1, row + col));
    } // метод нахождения алгебраческого дополнения для элемента строки под номером row и столбца под номером col

    public Value determinant() throws NonSquareMatrixException {
        if (height != width)
            throw new NonSquareMatrixException();

        if (height == 2)
            return getValue(0, 0).multiply(getValue(1, 1)).subtract(getValue(0, 1).multiply(getValue(1, 0)));

        if (height == 1)
            return getValue(0, 0);

        Value result = new Value();
        for (int i = 0; i < width; i++)
            result.increase(getValue(i, 0).multiply(complement(i, 0)));
        return result;
    } // рекурсивный метод нахождения детерминанта (сложность алгоритма растет экспоненциально)
    // _____________________________________

    // набор вычисления быстрого детерминанта
    // ______________________________________
    public Value fastMinor(int row, int col){
        return minorMatrix(row, col).fastDeterminant();
    }

    public Value fastComplement(int row, int col){
        return fastMinor(row, col).multiply((int)Math.pow(-1, row + col));
    }

    public Value fastDeterminant(){
        if (height != width)
            throw new NonSquareMatrixException();

        Matrix mx = getClone();
        Value zero = new Value(0);
        Value coeff = new Value(1);
        int i;
        while (mx.height > 2){

            for (i = 0; i < mx.height; i++){
                if (mx.getValue(i, 0).compare(zero) != 0) {
                    coeff.repeat(mx.getValue(i, 0));
                    mx.getRow(i).reduce(mx.getValue(i, 0).getClone());
                    if (mx.getValue(0, 0).compare(zero) == 0)
                        mx.getRow(0).increase(mx.getRow(i));
                }
                else if (mx.getRow(i).multiply(mx.getRow(i)).compare(zero) == 0 || mx.getCol(i).multiply(mx.getCol(i)).compare(zero) == 0)
                    return zero.getClone();
            }

            for (i = 1; i < mx.height; i++){
                if (mx.getValue(i, 0).compare(zero) != 0)
                    mx.getRow(i).decrease(mx.getRow(0));
            }

            mx = mx.minorMatrix(0, 0);
        }

        return mx.determinant().multiply(coeff);
    }
    // ______________________________________

    // точное вычисление обратной матрицы
    public Matrix opposite(){
        Matrix result = new Matrix(height, width);
        int i, j;
        Value det = determinant();
        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++)
                result.setValue(i, j, complement(i, j).divide(det));
        result.transpose();
        return result;
    }

    // быстрое вычисление обратной матрицы
    public Matrix fastOpposite(){
        Matrix result = new Matrix(height, width);
        int i, j;
        Value det = fastDeterminant();
        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++)
                result.setValue(i, j, fastComplement(i, j).divide(det));
        result.transpose();
        return result;
    }


    // перевод в String
    @Override
    public String toString(){
        return toString("\n", "\t");
    }

    public String toString(String rowDivider, String colDivider){
        int i, j, maxLen = 0, len;
        for (i = 0; i < height; i++){
            for (j = 0; j < width; j++){
                len = getValue(i, j).toString().length();
                if (len > maxLen){
                    maxLen = len;
                }
            }
        }

        StringBuilder output = new StringBuilder();
        for (i = 0; i < height - 1; i++){
            output.append(rows[i].toString(maxLen, colDivider)).append(rowDivider);
        }
        output.append(rows[i].toString(maxLen, colDivider));
        return output.toString();
    }


    // управление содержимым
    public void setValue(int row, int col, Value val){
        getRow(row).setValue(col, val);
    }

    public void setCell(int row, int col, Cell cell){
        getRow(row).setCell(col, cell);
        getCol(col).setCell(row, cell);
    }

    // доступ к содержимому
    public Value getValue(int row, int col){
        return rows[row].getValue(col);
    }

    public Cell getCell(int row, int col) {
        return rows[row].getCell(col);
    }

    public Vector getRow(int row){
        return rows[row];
    }

    public Vector getCol(int col){
        return cols[col];
    }

    public Matrix getClone(){
        Matrix result = new Matrix(height, width);
        int i, j;
        for (i = 0; i < height; i++)
            for (j = 0; j < width; j++)
                result.setValue(i, j, getValue(i, j).getClone());
        return result;
    }


    // служебные методы
    private void init(){
        // инициализация rows
        rows = new Vector[height];
        for (int i = 0; i < height; i++){
            rows[i] = new Vector(width);
        }
        // инициализация cols
        cols = new Vector[width];
        for (int i = 0; i < width; i++){
            cols[i] = new Vector(height);
        }
    }

    public String colsToString(){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < width; i++){
            output.append(cols[i].toString()).append("\n");
        }
        return output.toString();
    } // используется для отладки
}
