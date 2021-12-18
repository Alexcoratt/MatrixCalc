package resources.new_data_types;

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
                result.setValue(i, j, getValue(i, j).sum(other));
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
        for (i = 0; i < height; i++){
            output.append(rows[i].toString(maxLen, colDivider)).append(rowDivider);
        }
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
