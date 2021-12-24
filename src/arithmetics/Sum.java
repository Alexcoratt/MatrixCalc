package arithmetics;

import data_types.Matrix;
import data_types.Value;
import exceptions.WrongVarTypeException;

public class Sum extends Operator{

    public Sum(){
        symbol = '+';
    }

    @Override
    public Object calc(Object var1, Object var2) throws WrongVarTypeException {

        if (var1.getClass() == Matrix.class) {
            if (var2.getClass() == Matrix.class)
                return ((Matrix) var1).sum((Matrix) var2);
            else if (var2.getClass() == Value.class)
                return ((Matrix) var1).sum((Value) var2);
        }
        else if (var1.getClass() == Value.class)
            if (var2.getClass() == Matrix.class)
                return ((Matrix) var2).sum((Value) var1);
            else if (var2.getClass() == Value.class)
                return ((Value) var1).sum((Value) var2);

        throw new WrongVarTypeException();
    }
}
