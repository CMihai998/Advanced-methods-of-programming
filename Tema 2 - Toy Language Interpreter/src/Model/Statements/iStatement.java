package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Structures.MyIDictionary;
import Model.Types.Type;

public interface iStatement {
    PrgState execute(PrgState state) throws MyException;
    String toString();
    iStatement deepcopy() throws MyException;
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException;
}
