package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Structures.MyIDictionary;
import Model.Structures.iHeap;
import Model.Values.IntValue;
import Model.Values.Value;

public class ValueExpression implements iExpression {
   private Value val;

    public ValueExpression(Value intValue) {
        this.val = intValue;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, iHeap<Integer, Value> heapTable) throws MyException {
        return val;
    }
    @Override
    public String toString(){
        return val.toString();
    }

    @Override
    public iExpression deepcopy() throws MyException {
        return new ValueExpression(this.val);
    }
}
