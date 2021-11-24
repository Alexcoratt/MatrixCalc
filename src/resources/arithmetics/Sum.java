package resources.arithmetics;
import resources.data_types.*;

public class Sum extends Operation {

    public Sum(Matrix ...agrs) {
        super(agrs);
        this.description = "Sum operation";
    }

    @Override
    public Matrix calculate() {
        Matrix result = this.args[0];
        for (int i = 1; i < this.args.length; i++){
            result = result.sum(this.args[i]);
        }
        return result;
    }
}
