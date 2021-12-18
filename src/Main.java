import resources.new_data_types.*;
import resources.new_data_types.Value;

public class Main {
    public static void main(String[] args){
        Value[][] arr = {
                {new Value(3), new Value(1), new Value(-2)},
                {new Value(2), new Value(-10.5), new Value(-2)}
        };
        Matrix mx = new Matrix(arr);
        Matrix mx1 = mx.getClone();
        mx1.transpose();

        System.out.println(mx1);
        System.out.println();
        System.out.println(mx);
        System.out.println();
        System.out.println(mx1.multiply(mx));
    }
}

// ДЛЯ ВЫЧИСЛЕНИЙ ИСПОЛЬЗУЙ ДЕРЕВЬЯ С ПРЯМЫМ ОБХОДОМ!!!!!!
// (a + b) * (c + d)
// *(+(a, b), +(c, d)) или *+ab+cd