package resources.data_types;
import resources.arithmetics.Rand;
import resources.exceptions.*;

import java.math.BigDecimal;

public class Matrix {
    private BigDecimal[][] lines;
    private short height, width;
    private Rand rand;

    public Matrix(BigDecimal[][] lines) throws MatrixErrorExeption {
        this.lines = lines;
        short height = (short)this.lines.length, width = (short)this.lines[0].length;
        this.height = height;
        this.width = width;
        if (!this.checkWidth()) {
            throw new MatrixErrorExeption();
        }
        rand = new Rand();
    }

    public Matrix(short height, short width) throws MatrixSizeOutException {
        this.height = height;
        this.width = width;
        this.lines = new BigDecimal[height][width];
        rand = new Rand();
    }

    public Matrix(int height, int width){
        this((short) height, (short) width);
    }

    public void setValue(short row, short col, BigDecimal value){
        this.lines[row][col] = value;
    }

    public void setValue(short row, short col, double value){ this.setValue(row, col, BigDecimal.valueOf(value)); }

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

    public void setColumn(short colNum, BigDecimal[] array){
        for (short i = 0; i < this.getHeight(); i++){
            this.setValue(i, colNum, array[i]);
        }
    }

    public void setColumn(int colNum, BigDecimal[] array){
        this.setColumn((short)colNum, array);
    }

    public void setRow(short rowNum, BigDecimal[] array){
        for (short j = 0; j < this.getWidth(); j++){
            this.setValue(rowNum, j, array[j]);
        }
    }

    public void setRow(int rowNum, BigDecimal[] array){
        this.setRow((short)rowNum, array);
    }

    public void setLine(short lineNum, Matrix line) throws MatrixSizeOutException {
        if (line.getWidth() == 1){
            this.setColumn(lineNum, line.toArray());
        }
        else if (line.getHeight() == 1){
            this.setRow(lineNum, line.toArray());
        }
        else throw new MatrixSizeOutException();
    }   // устанавливает значение линии под номером lineNum (линия - строка или столбец, в зависимости от вида матрицы line)

    public void setLine(int lineNum, Matrix line){
        setLine((short)lineNum, line);
    }

    public Matrix getColumn(short colNum){
        Matrix result = new Matrix(this.getHeight(), 1);
        for (short i = 0; i < this.getHeight(); i++){
            result.setValue(i, (short)0, this.getValue(i, colNum));
        }
        return result;
    }

    public Matrix getColumn(int colNum){
        return this.getColumn((short) colNum);
    }

    public Matrix getRow(short rowNum){
        Matrix result = new Matrix(1, this.getWidth());
        for (short j = 0; j < this.getWidth(); j++){
            result.setValue((short)0, j, this.getValue(rowNum, j));
        }
        return result;
    }

    public Matrix getRow(int rowNum){
        return this.getRow((short) rowNum);
    }

    public BigDecimal[] toArray(){
        BigDecimal[] result = new BigDecimal[getWidth() * this.getHeight()];
        short i, j;
        for (i = 0; i < this.getHeight(); i++){
            for (j = 0; j < this.getWidth(); j++){
                result[this.getWidth() * i + j] = this.getValue(i, j);
            }
        }
        return result;
    } // переводит матрицу в одномерный массив

    public BigDecimal getValue(short row, short col){
        return this.lines[row][col];
    }

    public BigDecimal getValue(int row, int col){
        return this.getValue((short) row, (short) col);
    }

    public short getWidth() {
        return width;
    }

    public short getHeight() {
        return height;
    }

    public BigDecimal[][] getLines() {
        BigDecimal[][] result = new BigDecimal[this.getHeight()][this.getWidth()];
        short i, j;
        for (i = 0; i < this.getHeight(); i++){
            for (j = 0; j < this.getWidth(); j++){
                result[i][j] = lines[i][j];
            }
        }
        return result;
    }

    private boolean checkWidth(Matrix mx){
        for (BigDecimal[] line : mx.getLines()){
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
        for (BigDecimal[] line : this.getLines()){
            for (BigDecimal num : line){
                result.append(num).append(num_div);
            }
            result.append(line_div);
        }
        return result.toString();
    }

    static short getNumLength(BigDecimal num){
        return (short)num.toString().length();
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
                result.setValue(i, j, this.getValue(i, j).add(other.getValue(i, j)));
            }
        }
        return result;
    }

    public void fillZero(){
        this.lines = this.mul(0).getLines();
    }   // заполнение матрицы нулями

    public void makeIdentity(){
        this.fillZero();
        for (short i = 0; i < Math.min(this.getWidth(), this.getHeight()); i++){
            this.setValue(i, i, 1);
        }
    }   // превращение в единичную матрицу

    public Matrix transposed(){
        double tmp;
        Matrix result = this.getClone();
        short i, j;
        for (i = 0; i < this.getHeight(); i++){
            for (j = 0; j < this.getWidth(); j++){
                result.setValue(j, i, this.getValue(i, j));
                result.setValue(i, j, this.getValue(j, i));
            }
        }
        return result;
    }

    public Matrix opposite() throws DegenerateMatrixException{
        BigDecimal determinant = this.cycledDeterminant();
        if (determinant.equals(BigDecimal.valueOf(0))){
            throw new DegenerateMatrixException();
        }

        return this.adjugate().mul(BigDecimal.valueOf(1).divide(determinant));
    }

    public Matrix adjugate(){
        Matrix result = new Matrix(this.getHeight(), this.getWidth());
        short i, j;
        for (i = 0; i < this.getHeight(); i++){
            for (j = 0; j < this.getWidth(); j++){
                result.setValue(i, j, this.fastComplement(j, i));
            }
        }
        return result;
    }   // метод нахождения присоединенной (союзной/взаимной) матрицы

    public Matrix mul(Matrix other) throws MatrixErrorExeption{
        if (other.getClass() == SingleNum.class)
            return this.mul(other.getValue(0, 0));
        if (this.getWidth() != other.getHeight())
            throw new UnacceptableSizeMarixException();
        short i, j, num;
        Matrix result = new Matrix(this.getHeight(), other.getWidth());
        BigDecimal singleNum;
        BigDecimal[] row, col;
        for (i = 0; i < result.getHeight(); i++){
            for (j = 0; j < result.getWidth(); j++){
                singleNum = BigDecimal.valueOf(0);
                row = this.getRow(i).toArray();
                col = other.getColumn(j).toArray();
                for (num = 0; num < row.length; num++){
                    singleNum = singleNum.add(row[num].multiply(col[num]));
                }
                result.setValue(i, j, singleNum);
            }
        }
        return result;
    }

    public Matrix mul(BigDecimal other){
        short i, j;
        Matrix result;
        result = new Matrix(this.getHeight(), this.getWidth());
        for (i = 0; i < this.getHeight(); i++) {
            for (j = 0; j < this.getWidth(); j++) {
                result.setValue(i, j, this.getValue(i, j).multiply(other));
            }
        }
        return result;
    }

    public Matrix mul(double other){
        return this.mul(BigDecimal.valueOf(other));
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

    public BigDecimal complement(short row, short col) {
        return this.minor(row, col).determinant().multiply(BigDecimal.valueOf(Math.pow(-1, row + col)));
    }   // алгебраическое дополнение, основанное на рекуррентном методе детерминирования

    public BigDecimal complement(int row, int col) {
        return this.complement((short) row, (short) col);
    }

    public BigDecimal fastComplement(short row, short col) {
        return this.minor(row, col).cycledDeterminant().multiply(BigDecimal.valueOf(Math.pow(-1, row + col)));
    }   // алгебраическое дополнение, основанное на циклическом методе детерминирования

    public BigDecimal fastComplement(int row, int col){
        return this.fastComplement((short) row, (short) col);
    }

    public BigDecimal determinant() throws NonSquareMatrixException {
        if (this.getWidth() != this.getHeight()){
            throw new NonSquareMatrixException();
        }
        if (this.getWidth() == 2){
            return this.getValue(0, 0).multiply(this.getValue(1, 1)).subtract(
                    this.getValue(0, 1).multiply(this.getValue(1, 0)));
        }
        BigDecimal result = BigDecimal.valueOf(0);
        for (short j = 0; j < this.getWidth(); j++){
            result = result.add(this.getValue(0, j).multiply(complement((short)0, j)));
        }
        return result;
    }   // метод нахождения точного значения детерминанта матрицы
        // отличается крайней медлителльностью при работе с матрицами, длина стороны которых превосходит 10
        // сложность алгоритма растет экспоненциально

    public Matrix colSum(short numCol1, short numCol2, BigDecimal coeff){
        return this.getColumn(numCol1).sum(this.getColumn(numCol2).mul(coeff));
    }

    public Matrix colSum(short numCol1, short numCol2, double coeff){
        return this.colSum(numCol1, numCol2, BigDecimal.valueOf(coeff));
    }

    public Matrix colSum(short numCol1, short numCol2){
        return this.colSum(numCol1, numCol2, 1);
    }

    public Matrix rowSum(short numRow1, short numRow2, BigDecimal coeff){
        return this.getRow(numRow1).sum(this.getRow(numRow2).mul(coeff));
    }

    public Matrix rowSum(short numRow1, short numRow2, double coeff){
        return this.rowSum(numRow1, numRow2, BigDecimal.valueOf(coeff));
    }

    public Matrix rowSum(short numRow1, short numRow2){
        return this.rowSum(numRow1, numRow2, 1);
    }

    public Matrix getClone(){
        return new Matrix(this.getLines());
    }

    public boolean isZeroMx(){
        BigDecimal result = BigDecimal.valueOf(0);
        for (BigDecimal elem : this.toArray()){
            result = result.add(elem.abs());
        }
        return (result.equals(BigDecimal.valueOf(0)));
    }   // возвращает true, если данная матрица нулевая

    public BigDecimal cycledDeterminant() throws NonSquareMatrixException {
        BigDecimal coeff = BigDecimal.valueOf(1);
        BigDecimal topValue;
        Matrix mx = this.getClone();
        short j, num = 1; // номер прибавляемой в следующем цикле строки или столбца
        if (mx.getWidth() != mx.getHeight()){
            throw new NonSquareMatrixException();
        }
        while (mx.getWidth() > 2){

            for (j = 0; j < mx.getWidth(); j++){
                if (mx.getColumn(j).isZeroMx() || mx.getRow(j).isZeroMx()){
                    return BigDecimal.valueOf(0);
                }
            }

            while (mx.getValue(0, 0).equals(BigDecimal.valueOf(0))){
                mx.setLine(0, mx.colSum((short)0, num));
                mx.setLine(0, mx.rowSum((short)0, num));
                num++;
            }   // прибавляем строки/столбцы к верхней строке/левому столбцу до тех пор,
            // пока верхнее левое верхнее значение не станет ненулевым

            for (j = 0; j < mx.getWidth(); j++){
                topValue = mx.getValue(0, j);
                if (!topValue.equals(BigDecimal.valueOf(0))) {
                    coeff = coeff.multiply(topValue);
                    mx.setLine(j, mx.getColumn(j).mul(BigDecimal.valueOf(1).divide(topValue)).
                            sum(mx.getColumn((short) 0).mul((j > 0) ? -1 : 0)));
                }
            }

            mx = mx.minor(0, 0);
        }

        return (mx.getValue(0, 0).multiply(mx.getValue(1, 1)).
                subtract(mx.getValue(0, 1).multiply(mx.getValue(1, 0)))).multiply(coeff);
    }   // метод нахождения определителя матрицы с помощью цикла, а не рекурсии. Работает быстро, тратит меньше памяти

    public BigDecimal fastDeterminant(Matrix mx) throws NonSquareMatrixException {
        if (mx.getWidth() != mx.getHeight()){
            throw new NonSquareMatrixException();
        }
        if (mx.getWidth() == 2){
            return mx.getValue(0, 0).multiply(mx.getValue(1, 1)).
                    subtract(mx.getValue(0, 1).multiply(mx.getValue(1, 0)));
        }
        BigDecimal topValue;  // при умножении строки или столбца матрицы на ненулевое значение
        BigDecimal coeff = BigDecimal.valueOf(1);
        // определитель умножается на него же (а значит требуется деление для получения исходного значания)
        short j, num = 1; // номер прибавляемой в следующем цикле строки или столбца

        for (j = 0; j < mx.getWidth(); j++){
            if (mx.getColumn(j).isZeroMx() || mx.getRow(j).isZeroMx()){
                return BigDecimal.valueOf(0);
            }
        }

        while (mx.getValue(0, 0).equals(BigDecimal.valueOf(0))){
            mx.setLine(0, mx.colSum((short)0, num));
            mx.setLine(0, mx.rowSum((short)0, num));
            num++;
        }   // прибавляем строки/столбцы к верхней строке/левому столбцу до тех пор,
            // пока верхнее левое верхнее значение не станет ненулевым

        for (j = 0; j < mx.getWidth(); j++){
            topValue = mx.getValue(0, j);
            if (!topValue.equals(BigDecimal.valueOf(0))) {
                coeff = coeff.multiply(topValue);
                mx.setLine(j, mx.getColumn(j).mul(BigDecimal.valueOf(1).divide(topValue)).
                        sum(mx.getColumn((short) 0).mul((j > 0) ? -1 : 0)));
            }
        }

        return mx.minor(0, 0).fastDeterminant().multiply(coeff);
    }   // функция быстрого нахождения детерминанта матрицы
        // имеет пониженную точность (относительная погрешность не превосходит 1.0E-13),
        // в отличие от this.determinant(), но многократно превосходит его по скорости

    public BigDecimal fastDeterminant() throws NonSquareMatrixException {
        return this.fastDeterminant(this.getClone());
    }
}
