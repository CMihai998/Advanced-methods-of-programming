package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Structures.MyIDictionary;
import Model.Structures.iHeap;
import Model.Values.Value;

public interface iExpression {
    Value evaluate(MyIDictionary<String, Value> table, iHeap<Integer, Value> heapTable) throws MyException;
    String toString();
    iExpression deepcopy() throws MyException;
}
