import resources.Parser;
import resources.arithmetics.*;
import resources.data_types.*;

public class Main {
    public static void main(String[] args){
        short size = 3;
        Matrix mx1 = new Matrix(size, size);
        mx1.fillRandomValues(-1, 1);
        Matrix mx2 = mx1.getClone();
        mx2.makeIdentity();
        System.out.println(mx1);
        System.out.println(mx1.mul(mx2));
    }
}

// ДЛЯ ВЫЧИСЛЕНИЙ ИСПОЛЬЗУЙ ДЕРЕВЬЯ С ПРЯМЫМ ОБХОДОМ!!!!!!
// (a + b) * (c + d)
// *(+(a, b), +(c, d)) или *+ab+cd