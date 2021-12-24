package data_types;

public class Cell {
    public Value value;

    public Cell(Value value){
        this.value = value;
    }

    public Cell(){
        value = new Value();
    }

    public Cell getClone() {
        return new Cell(value.getClone());
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
