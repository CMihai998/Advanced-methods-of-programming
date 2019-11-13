package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value {
    boolean value;

    public BoolValue(){ value = false;}

    public BoolValue(boolean v){value = v;}

    public boolean getValue(){return value;}

    public String toString() {return Boolean.toString(value);}

    @Override
    public boolean equals(Object another){
        if(another instanceof BoolValue && ((BoolValue) another).getValue() == value)
            return true;
        else
            return false;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepcopy() {
        return new BoolValue(this.value);
    }
}
