package resources.data_types;

import resources.exceptions.DifferentSizeMatrixesException;
import resources.exceptions.NonSquareMatrixException;

import java.math.BigDecimal;

public class SingleNum extends Matrix {
    public SingleNum(BigDecimal num){
        super(1, 1);
        this.setValue((short) 0, (short) 0, num);
    }

    public BigDecimal getValue() {
        return super.getValue(0, 0);
    }

    @Override
    public Matrix mul(Matrix other) {
        if (other.getClass() == SingleNum.class){
            return new SingleNum(this.getValue().multiply(((SingleNum) other).getValue()));
        }
        return other.mul(this);
    }

    @Override
    public Matrix sum(Matrix other) throws DifferentSizeMatrixesException {
        if (other.getClass() != SingleNum.class){
            throw new DifferentSizeMatrixesException();
        }
        return new SingleNum(this.getValue().add(((SingleNum) other).getValue()));
    }

    @Override
    public BigDecimal determinant() {
        return this.getValue();
    }

    @Override
    public BigDecimal fastDeterminant() {
        return this.getValue();
    }
}
