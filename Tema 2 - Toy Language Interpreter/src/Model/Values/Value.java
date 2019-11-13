package Model.Values;


import Model.Exceptions.MyException;
import Model.Types.Type;

public interface Value {
    boolean equals(Object another);
    Type getType();
    Value deepcopy();
    //Value getValue();
}
