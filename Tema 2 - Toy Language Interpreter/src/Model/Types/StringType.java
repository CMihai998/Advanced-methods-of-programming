package Model.Types;

import Model.Values.StringValue;
import Model.Values.Value;

public class StringType implements Type {
    String value;

    @Override
    public boolean equals(Object another){
        if(another instanceof StringType)
            return true;
        else
            return false;
    }

    public String toString(){return "String";}

    public String getValue(){return value;}


    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public Type deepcopy() {
        return new StringType();
    }
}
