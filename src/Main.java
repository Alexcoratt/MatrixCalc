import parser.Parser;

public class Main {
    public static void main(String[] args){
        System.out.println("Добро пожаловать! Перед вами Матричный калькулятор\nВведите \"help\", чтобы начать");
        Parser prs = new Parser();
        prs.loop();
    }
}
