package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Structures.MyIDictionary;
import Model.Values.Value;

public class VariableExpression implements iExpression {
    String id;

    public VariableExpression(String id){
        this.id = id;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table) throws MyException {
        return table.get(id);
    }

    @Override
    public String toString(){
        return id;
    }

    @Override
    public iExpression deepcopy() throws MyException {
        return new VariableExpression(this.id);
    }
}
