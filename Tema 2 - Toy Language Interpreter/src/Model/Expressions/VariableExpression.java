package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Structures.MyIDictionary;
import Model.Structures.iHeap;
import Model.Types.Type;
import Model.Values.Value;

public class VariableExpression implements iExpression {
    String id;

    public VariableExpression(String id){
        this.id = id;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, iHeap<Integer, Value> heapTable) throws MyException {
        return table.get(id);
    }

    @Override
    public String toString(){
        return id;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment.get(id);
    }

    @Override
    public iExpression deepcopy() throws MyException {
        return new VariableExpression(this.id);
    }
}
