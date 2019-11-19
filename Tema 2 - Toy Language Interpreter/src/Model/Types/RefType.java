package Model.Types;

import Model.Values.RefValue;
import Model.Values.Value;


public class RefType implements Type {
    Type wrapped;

    public RefType(Type wrapped){
        this.wrapped = wrapped;
    }

    public Type getWrapped(){
        return wrapped;
    }

    @Override
    public boolean equals(Object another){
        if(another instanceof RefType){
            return wrapped.equals(((RefType) another).getWrapped());
        }
        return false;
    }

    @Override
    public String toString(){
        return "Ref( " + wrapped.toString() + " )";
    }



    @Override
    public Value defaultValue() {
        return new RefValue(0, wrapped);
    }

    @Override
    public Type deepcopy() {
        return new RefType(wrapped);
    }
}
