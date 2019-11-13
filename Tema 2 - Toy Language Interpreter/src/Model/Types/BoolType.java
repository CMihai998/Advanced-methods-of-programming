package Model.Types;

import Model.Values.BoolValue;
import Model.Values.Value;

public class BoolType implements Type {
    boolean value;

    @Override
    public boolean equals(Object another){
        if(another instanceof BoolType)
            return true;
        else
            return false;
    }

    public String toString(){return "bool";}

    public boolean getValue(){
        return value;
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deepcopy() {
        return new BoolType();
    }
}
