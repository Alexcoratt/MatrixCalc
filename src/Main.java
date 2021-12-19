import resources.data_types.*;
import resources.data_types.Value;

public class Main {
    public static void main(String[] args){
        Value[][] arr = {
                {new Value(3), new Value(2), new Value(2)},
                {new Value(1), new Value(-5), new Value(-8)},
                {new Value(4), new Value(2), new Value(1)}
        };
        Matrix mx = new Matrix(arr);

        System.out.println(mx);
        System.out.println(mx.power(-2).multiply(mx.multiply(mx)));
    }
}

// ДЛЯ ВЫЧИСЛЕНИЙ ИСПОЛЬЗУЙ ДЕРЕВЬЯ С ПРЯМЫМ ОБХОДОМ!!!!!!
// (a + b) * (c + d)
// *(+(a, b), +(c, d)) или *+ab+cd