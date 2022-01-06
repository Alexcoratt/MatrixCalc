import parser.Parser;

public class Main {
    public static void main(String[] args){
        System.out.println("Добро пожаловать! Перед вами Матричный калькулятор");
        Parser prs = new Parser();
        prs.loop();
    }
}
