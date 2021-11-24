package resources.data_types;
import resources.arithmetics.Rand;
import resources.exceptions.*;

public class Matrix {
    public final short MAX_SIZE = 1024;
    private double[][] lines;
    private short height, width;
    private Rand rand;

    public Matrix(double[] ...lines) throws MatrixErrorExeption {
        this.lines = lines;
        short height = (short)this.lines.length, width = (short)this.lines[0].length;
        //if (width * height > MAX_SIZE){throw new MatrixSizeOutException();}
        this.height = height;
        this.width = width;
        if (!this.checkWidth()) {
            throw new MatrixErrorExeption();
        }
        rand = new Rand();
    }

    public Matrix(short height, short width) throws MatrixSizeOutException {
        //if (width * height > MAX_SIZE){throw new MatrixSizeOutException();}
        this.height = height;
        this.width = width;
        this.lines = new double[height][width];
        rand = new Rand();
    }

    public Matrix(int height, int width){
        this((short) height, (short) width);
    }

    public void setValue(short row, short col, double value){
        this.lines[row][col] = value;
    }

    public void setValue(short row, short col){
        this.setValue(row, col, 0);
    }

    public void setRandomValue(short row, short col, double min, double max){
        this.setValue(row, col, this.rand.randrange(min, max));
    }

    public void fillRandomValues(double min, double max){
        short i, j;
        for (i = 0; i < this.getHeight(); i++){
            for (j = 0; j < this.getWidth(); j++){
                this.setRandomValue(i, j, min, max);
            }
        }
    }

    public void setColumn(short colNum, double[] array){
        for (short i = 0; i < this.getHeight(); i++){
            this.setValue(i, colNum, array[i]);
        }
    }

    public void setRow(short rowNum, double[] array){
        for (short j = 0; j < this.getWidth(); j++){
            this.setValue(rowNum, j, array[j]);
        }
    }

    public void setLine(short lineNum, Matrix line) throws MatrixSizeOutException {
        if (line.getWidth() == 1){
            for (short i = 0; i < line.getHeight(); i++){
                this.setValue(i, lineNum, line.getValue(i, 0));
            }
        }
        else if (line.getHeight() == 1){
            for (short j = 0; j < line.getWidth(); j++){
                this.setValue(lineNum, j, line.getValue(0, j));
            }
        }
        else throw new MatrixSizeOutException();
    }   // устанавливает значение линии под номером lineNum (линия - строка или столбец, в зависимости от вида матрицы line)

    public Matrix getColumn(short colNum){
        Matrix result = new Matrix(this.getHeight(), 1);
        for (short i = 0; i < this.getHeight(); i++){
            result.setValue(i, (short)0, this.getValue(i, colNum));
        }
        return result;
    }

    public Matrix getRow(short rowNum){
        Matrix result = new Matrix(1, this.getWidth());
        for (short j = 0; j < this.getWidth(); j++){
            result.setValue((short)0, j, this.getValue(rowNum, j));
        }
        return result;
    }

    public double[] toArray(){
        double[] result = new double[getWidth() * this.getHeight()];
        short i, j;
        for (i = 0; i < this.getHeight(); i++){
            for (j = 0; j < this.getWidth(); j++){
                result[this.getWidth() * i + j] = this.getValue(i, j);
            }
        }
        return result;
    } // переводит матрицу в одномерный массив

    public double getValue(short row, short col){
        return this.lines[row][col];
    }

    public double getValue(int row, int col){
        return this.getValue((short) row, (short) col);
    }

    public short getWidth() {
        return width;
    }

    public short getHeight() {
        return height;
    }

    public double[][] getLines() {
        double[][] result = new double[this.getHeight()][this.getWidth()];
        short i, j;
        for (i = 0; i < this.getHeight(); i++){
            for (j = 0; j < this.getWidth(); j++){
                result[i][j] = lines[i][j];
            }
        }
        return result;
    }

    private boolean checkWidth(Matrix mx){
        for (double[] line : mx.getLines()){
            if (line.length != mx.getWidth()){
                return false;
            }
        }
        return true;
    }

    private boolean checkWidth() {
        return this.checkWidth(this);
    }

    public String toString(String line_div, String num_div){
        StringBuilder result = new StringBuilder();
        for (double[] line : this.getLines()){
            for (double num : line){
                result.append(num).append(num_div);
            }
            result.append(line_div);
        }
        return result.toString();
    }

    static short getNumLength(double num){
        return (short)Double.toString(num).length();
    }

    static String strMul(String str, int num){
        StringBuilder result = new StringBuilder();
        while (num-- > 0){
            result.append(str);
        }
        return result.toString();
    }

    public String toString(String line_div){

        short i, j, length, max_length=getNumLength(this.getValue((short)0, (short)0));
        for (i = 0; i < this.getHeight(); i++) {
            for (j = 0; j < this.getWidth(); j++){
                length = getNumLength(this.getValue(i, j));
                if (length > max_length){
                    max_length = length;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (i = 0; i < this.getHeight(); i++){
            for (j = 0; j < this.getWidth(); j++){
                result.append(this.getValue(i, j)).append(strMul("\t", max_length / 4 -
                        getNumLength(this.getValue(i, j)) / 4 + 1));
            }
            result.append(line_div);
        }
        return result.toString();
    }

    @Override
    public String toString(){
        return toString("\n");
    }

    public Matrix sum(Matrix other) throws DifferentSizeMatrixesException{
        if (this.getHeight() * other.getWidth() != other.getHeight() * this.getWidth()){
            throw new DifferentSizeMatrixesException();
        }
        Matrix result = new Matrix(this.getHeight(), this.getWidth());
        short i, j;
        for (i = 0; i < this.getHeight(); i++){
            for (j = 0; j < this.getWidth(); j++){
                result.setValue(i, j, this.getValue(i, j) + other.getValue(i, j));
            }
        }
        return result;
    }

    public Matrix mul(Matrix other) throws MatrixErrorExeption{
        if (other.getClass() == SingleNum.class)
            return this.mul(other.getValue(0, 0));
        if (this.getWidth() != other.getHeight())
            throw new UnacceptableSizeMarixException();
        short i, j, num;
        Matrix result = new Matrix(this.getHeight(), other.getWidth());
        double singleNum;
        double[] row, col;
        for (i = 0; i < result.getHeight(); i++){
            for (j = 0; j < result.getWidth(); j++){
                singleNum = 0;
                row = this.getRow(i).toArray();
                col = other.getColumn(j).toArray();
                for (num = 0; num < row.length; num++){
                    singleNum += row[num] * col[num];
                }
                result.setValue(i, j, singleNum);
            }
        }
        return result;
    }

    public Matrix mul(double other){
        short i, j;
        Matrix result;
        result = new Matrix(this.getHeight(), this.getWidth());
        for (i = 0; i < this.getHeight(); i++) {
            for (j = 0; j < this.getWidth(); j++) {
                result.setValue(i, j, other * this.getValue(i, j));
            }
        }
        return result;
    }

    static byte shiftFunc(short x, short a){
        return (byte)Math.signum((float)((x - a + 1 + Math.abs(x - a + 1)) / 2));
    }   // функция сдвига функции на 1 после достижения a (значения исключаемой строки или столбца) (нужно для создания минора) _|¯

    public Matrix removeRow(short rowNum){
        Matrix result = new Matrix(this.getHeight() - 1, this.getWidth());
        for (short i = 0; i < result.getHeight(); i++){
            result.setLine(i, this.getRow((short)(i + shiftFunc(i, rowNum))));
        }
        return result;
    }

    public Matrix removeRow(int rowNum){
        return removeRow((short)rowNum);
    }

    public Matrix removeColumn(short colNum){
        Matrix result = new Matrix(this.getHeight(), this.getWidth() - 1);
        for (short j = 0; j < result.getWidth(); j++){
            result.setLine(j, this.getColumn((short)(j + shiftFunc(j, colNum))));
        }
        return result;
    }

    public Matrix removeColumn(int rowNum){
        return removeColumn((short)rowNum);
    }

    public Matrix minor(short row, short col){
        Matrix result = new Matrix(this.getHeight() - 1, this.getWidth() - 1);
        short i, j;
        int i1, j1;
        for (i = 0; i < result.getHeight(); i++){
            for (j = 0; j < result.getWidth(); j++){
                i1 = i + shiftFunc(i, row);
                j1 = j + shiftFunc(j, col);
                result.setValue(i, j, this.getValue(i1, j1));
            }
        }
        return result;
    }

    public Matrix minor(int row, int col){
        return this.minor((short) row, (short) col);
    }

    public double complement(short row, short col) {
        return Math.pow(-1, row + col) * this.minor(row, col).determinant();
    }

    public double determinant() throws NonSquareMatrixException {
        if (this.getWidth() != this.getHeight()){
            throw new NonSquareMatrixException();
        }
        if (this.getWidth() == 2){
            return this.getValue(0, 0) * this.getValue(1, 1) -
                    this.getValue(0, 1) * this.getValue(1, 0);
        }
        double result = 0;
        for (short j = 0; j < this.getWidth(); j++){
            result += this.getValue(0, j) * complement((short)0, j);
        }
        return result;
    }   // метод нахождения точного значения детерминанта матрицы
        // отличается крайней медлителльностью при работе с матрицами, длина стороны которых превосходит 10
        // сложность алгоритма растет экспоненциально

    public Matrix colSum(short numCol1, short numCol2, double coeff){
        return this.getColumn(numCol1).sum(this.getColumn(numCol2).mul(coeff));
    }

    public Matrix colSum(short numCol1, short numCol2){
        return this.colSum(numCol1, numCol2, 1);
    }

    public Matrix rowSum(short numRow1, short numRow2, double coeff){
        return this.getRow(numRow1).sum(this.getRow(numRow2).mul(coeff));
    }

    public Matrix rowSum(short numRow1, short numRow2){
        return this.rowSum(numRow1, numRow2, 1);
    }

    private static boolean isZero(double[] array){
        double result = 0;
        for (double elem : array){
            result += Math.abs(elem);
        }
        return (result == 0);
    }

    public double fastDeterminant(Matrix mx) throws NonSquareMatrixException {
        if (mx.getWidth() != mx.getHeight()){
            throw new NonSquareMatrixException();
        }
        if (mx.getWidth() == 2){
            return mx.getValue(0, 0) * mx.getValue(1, 1) -
                    mx.getValue(0, 1) * mx.getValue(1, 0);
        }
        double topValue, coeff = 1; // при умножении строки или столбца матрицы на ненулевое значение
                                    // определитель умножается на него же
        short j, num = 1; // номер прибавляемой в следующем цикле строки или столбца

        for (j = 0; j < mx.getWidth(); j++){
            if (isZero(this.getColumn(j).toArray()) || isZero(this.getRow(j).toArray()))
                return 0;
        }

        while (mx.getValue(0, 0) == 0){
            mx.setLine((short)0, mx.colSum((short)0, num));
            mx.setLine((short)0, mx.rowSum((short)0, num));
        }   // прибавляем строки/столбцы к верхней строке/левому столбцу до тех пор,
            // пока верхнее левое верхнее значение не станет ненулевым

        for (j = 0; j < mx.getWidth(); j++){
            topValue = mx.getValue(0, j);
            if (topValue != 0) {
                coeff *= topValue;
                mx.setLine(j, mx.getColumn(j).mul(1 / topValue).sum(mx.getColumn((short) 0).mul((j > 0) ? -1 : 0)));
            }
        }
        double x = this.getWidth();
        return mx.minor(0, 0).fastDeterminant() * coeff;
    }   // функция быстрого нахождения детерминанта матрицы
        // имеет пониженную точность (относительная погрешность не превосходит 1.0E-13),
        // в отличие от this.determinant(), но многократно превосходит его по скорости

    public double fastDeterminant() throws NonSquareMatrixException {
        return this.fastDeterminant(new Matrix(this.getLines()));
    }

    public double getMax(){
        double[] array = this.toArray();
        double max = array[0];
        for (double value : array){
            if (max < value)
                max = value;
        }
        return max;
    }

    public double getMin(){
        double[] array = this.toArray();
        double min = array[0];
        for (double value : array){
            if (min > value)
                min = value;
        }
        return min;
    }
}
