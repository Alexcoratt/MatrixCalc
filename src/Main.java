import resources.Parser;
import resources.arithmetics.*;
import resources.data_types.*;

public class Main {
    public static void main(String[] args){
        short size = 10;
        Matrix randMx = new Matrix(new double[][]{
                {4, 5, 6, 7, 8},
                {1, 0, 2, 5, 0},
                {2, 3, 0, 0, 0},
                {10, 0, 0, 0, 0},
                {3, 0, 4, 6, 3}
        });
        randMx.fillRandomValues(-1, 1);

        System.out.println(randMx.determinant());
        System.out.println(randMx.fastDeterminant());
        System.out.println(randMx.cycledDeterminant());
    }
}

// ДЛЯ ВЫЧИСЛЕНИЙ ИСПОЛЬЗУЙ ДЕРЕВЬЯ С ПРЯМЫМ ОБХОДОМ!!!!!!
// (a + b) * (c + d)
// *(+(a, b), +(c, d)) или *+ab+cd