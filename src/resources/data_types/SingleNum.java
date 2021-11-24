package resources.data_types;

import resources.exceptions.DifferentSizeMatrixesException;
import resources.exceptions.NonSquareMatrixException;

public class SingleNum extends Matrix {
    public SingleNum(double num){
        super(1, 1);
        this.setValue((short) 0, (short) 0, num);
    }

    public double getValue() {
        return super.getValue(0, 0);
    }

    @Override
    public Matrix mul(Matrix other) {
        if (other.getClass() == SingleNum.class){
            return new SingleNum(this.getValue() * ((SingleNum) other).getValue());
        }
        return other.mul(this);
    }

    @Override
    public Matrix sum(Matrix other) throws DifferentSizeMatrixesException {
        if (other.getClass() != SingleNum.class){
            throw new DifferentSizeMatrixesException();
        }
        return new SingleNum(this.getValue() + ((SingleNum) other).getValue());
    }

    @Override
    public double determinant() {
        return this.getValue();
    }

    @Override
    public double fastDeterminant() {
        return this.getValue();
    }
}
