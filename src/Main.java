import resources.Parser;
import resources.commands.*;
import resources.data_types.*;

public class Main {
    public static void main(String[] args){
        Parser prs = new Parser();
        prs.loop();
    }
}

// ДЛЯ ВЫЧИСЛЕНИЙ ИСПОЛЬЗУЙ ДЕРЕВЬЯ С ПРЯМЫМ ОБХОДОМ!!!!!!
// (a + b) * (c + d)
// *(+(a, b), +(c, d)) или *+ab+cd