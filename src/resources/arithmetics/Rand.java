package resources.arithmetics;

public class Rand {
    public float randrange(double min, double max){
        return (float) (Math.random() * (max - min) + min);
    }
}
