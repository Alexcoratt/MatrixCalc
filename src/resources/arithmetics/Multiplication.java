package resources.arithmetics;

import resources.data_types.Matrix;
import resources.data_types.Value;
import resources.exceptions.WrongVarTypeException;

public class Multiplication extends Operator{
    public Multiplication(){
        symbol = '*';
    }

    @Override
    public Object calc(Object var1, Object var2) throws WrongVarTypeException {

        if (var1.getClass() == Matrix.class) {
            if (var2.getClass() == Matrix.class)
                return ((Matrix) var1).multiply((Matrix) var2);
            else if (var2.getClass() == Value.class)
                return ((Matrix) var1).multiply((Value) var2);
        }
        else if (var1.getClass() == Value.class)
            if (var2.getClass() == Matrix.class)
                return ((Matrix) var2).multiply((Value) var1);
            else if (var2.getClass() == Value.class)
                return ((Value) var1).multiply((Value) var2);

        throw new WrongVarTypeException();
    }
}
