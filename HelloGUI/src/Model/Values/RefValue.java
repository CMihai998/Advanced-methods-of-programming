package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;


public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int address, Type locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setLocationType(Type locationType) {
        this.locationType = locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepcopy() {
        return new RefValue(address, locationType.deepcopy());
    }

    @Override
    public String toString(){
        return "( " + this.address + ", " + locationType.toString() + " )";
    }
}
