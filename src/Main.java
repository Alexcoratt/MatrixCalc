import resources.new_data_types.*;
import resources.new_data_types.Value;

public class Main {
    public static void main(String[] args){
        Value arr[][] = {
                {new Value(3), new Value(1), new Value(-2)},
                {new Value(2), new Value(-10.5), new Value(-2)},
                {new Value(5), new Value(1), new Value(13)}
        };
        Matrix mx = new Matrix(arr);
        System.out.println(mx);
        mx.decrease(new Value(10));
        System.out.println(mx);
    }
}

// ДЛЯ ВЫЧИСЛЕНИЙ ИСПОЛЬЗУЙ ДЕРЕВЬЯ С ПРЯМЫМ ОБХОДОМ!!!!!!
// (a + b) * (c + d)
// *(+(a, b), +(c, d)) или *+ab+cd