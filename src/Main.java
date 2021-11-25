import resources.data_types.*;

public class Main {
    public static void main(String[] args){
        short size = 10;
        Matrix mx1 = new Matrix(size, size);
        mx1.fillRandomValues(-10, 10);
        System.out.println(mx1);
        System.out.println(mx1.mul(mx1.opposite()));
    }
}

// ДЛЯ ВЫЧИСЛЕНИЙ ИСПОЛЬЗУЙ ДЕРЕВЬЯ С ПРЯМЫМ ОБХОДОМ!!!!!!
// (a + b) * (c + d)
// *(+(a, b), +(c, d)) или *+ab+cd