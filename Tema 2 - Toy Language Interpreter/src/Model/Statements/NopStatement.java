package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;

public class NopStatement implements iStatement {

    public NopStatement(){}

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new NopStatement();
    }
}
