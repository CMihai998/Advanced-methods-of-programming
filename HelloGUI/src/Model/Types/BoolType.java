package Model.Types;

import Model.Values.BoolValue;
import Model.Values.Value;

public class BoolType implements Type {
    boolean value;

    @Override
    public boolean equals(Object another){
        return another instanceof BoolType;
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
