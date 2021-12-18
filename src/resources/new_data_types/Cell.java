package resources.new_data_types;

public class Cell {
    public Value value;
    public boolean isDeleted;

    public Cell(Value value){
        this.value = value;
        isDeleted = false;
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

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setDeleted(){
        isDeleted = true;
    }
}
