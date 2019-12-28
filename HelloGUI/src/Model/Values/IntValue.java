package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value {
    int value;

    public IntValue(){ value = 0;}

    public IntValue(int v){value = v;}

    public int getValue(){return value;}

    public String toString(){return Integer.toString(value);}

    @Override
    public boolean equals(Object another){
        return another instanceof IntValue && ((IntValue) another).getValue() == value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepcopy() {
        return new IntValue(this.value);
    }
}
