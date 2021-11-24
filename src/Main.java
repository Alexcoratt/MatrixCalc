import resources.Parser;
import resources.arithmetics.*;
import resources.data_types.*;

public class Main {
    public static void main(String[] args){
        short size = 10;
        Matrix randMx = new Matrix(size, size);
        randMx.fillRandomValues(-1, 1);

        System.out.print(randMx);
        System.out.println(randMx.fastDeterminant());
        System.out.println(randMx.determinant());
    }
}

// ДЛЯ ВЫЧИСЛЕНИЙ ИСПОЛЬЗУЙ ДЕРЕВЬЯ С ПРЯМЫМ ОБХОДОМ!!!!!!
// (a + b) * (c + d)
// *(+(a, b), +(c, d)) или *+ab+cd