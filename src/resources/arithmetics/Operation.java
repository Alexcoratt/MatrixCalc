package resources.arithmetics;
import resources.data_types.*;

public class Operation {
    public String description;
    public Matrix[] args;

    public Operation(Matrix ...args){
        this.description = "Basic operation";
        this.args = args;
    }

    public Matrix calculate(){
        return new Matrix(1, 1);
    }

    public void describe(){
        System.out.println(this.description);
    }
}
