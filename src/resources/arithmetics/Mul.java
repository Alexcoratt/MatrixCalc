package resources.arithmetics;
import resources.data_types.*;

public class Mul extends Operation {

    public Mul(Matrix ...args){
        super(args);
        this.description = "Multiple operation";
    }

    @Override
    public Matrix calculate() {
        Matrix result = this.args[0];
        for (int i = 1; i < this.args.length; i++){
            result = result.mul(this.args[i]);
        }
        return result;
    }
}
