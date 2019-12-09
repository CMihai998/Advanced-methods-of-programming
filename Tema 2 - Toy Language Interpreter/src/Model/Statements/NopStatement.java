package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Structures.MyIDictionary;
import Model.Types.Type;

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

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "NOP";
    }
}
