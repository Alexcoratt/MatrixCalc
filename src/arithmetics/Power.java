package arithmetics;

import data_types.Matrix;
import data_types.Value;
import exceptions.WrongVarTypeException;

public class Power extends Operator{
    public Power(){
        symbol = '^';
    }

    @Override
    public Object calc(Object var1, Object var2) throws WrongVarTypeException {
        if (var1.getClass() == Matrix.class && var2.getClass() == Value.class)
            return ((Matrix) var1).power((Value) var2);
        if (var1.getClass() == Value.class && var2.getClass() == Value.class)
            return ((Value) var1).power((Value) var2);
        throw new WrongVarTypeException();
    }
}
