package resources.arithmetics;

import java.math.BigDecimal;

public class Rand {
    public BigDecimal randrange(double min, double max){
        return BigDecimal.valueOf(Math.random() * (max - min) + min);
    }
}
