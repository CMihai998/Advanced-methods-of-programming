package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;

public interface iStatement {
    PrgState execute(PrgState state) throws MyException;
    String toString();
    iStatement deepcopy() throws MyException;
}
