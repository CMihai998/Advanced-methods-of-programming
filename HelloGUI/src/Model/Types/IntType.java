package Model.Types;

import Model.Values.IntValue;
import Model.Values.Value;

public class IntType implements Type {
    int value;

    @Override
    public boolean equals(Object another){
        return another instanceof IntType;
    }

    public String toString(){return "int";}

    public int getValue() {
        return value;
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type deepcopy() {
        return new IntType();
    }
}
