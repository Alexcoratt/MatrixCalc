import resources.new_data_types.*;
import resources.new_data_types.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args){
        Value[][] arr = {
                {new Value(3), new Value(2), new Value(2)},
                {new Value(1), new Value(-5), new Value(-8)},
                {new Value(4), new Value(2), new Value(1)}
        };
        Matrix mx = new Matrix(arr);

        System.out.println(mx);
        System.out.println(mx.determinant());
        System.out.println(mx.fastDeterminant());
    }
}

// ДЛЯ ВЫЧИСЛЕНИЙ ИСПОЛЬЗУЙ ДЕРЕВЬЯ С ПРЯМЫМ ОБХОДОМ!!!!!!
// (a + b) * (c + d)
// *(+(a, b), +(c, d)) или *+ab+cd