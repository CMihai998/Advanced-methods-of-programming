package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Structures.MyIDictionary;
import Model.Values.Value;

public interface iExpression {
    Value evaluate(MyIDictionary<String, Value> table) throws MyException;
    String toString();
    iExpression deepcopy() throws MyException;
}
