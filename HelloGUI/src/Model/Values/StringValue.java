package Model.Values;

import Model.Exceptions.MyException;
import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value {
    String value;

    public StringValue(){value = "";}

    public StringValue(String v){value = v;}

    public String getValue(){return value;}

    public String toString(){return value;}

    @Override
    public boolean equals(Object another) {
        return another instanceof StringValue && ((StringValue) another).getValue() == value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepcopy() {
        return new StringValue(this.value);
    }
}
