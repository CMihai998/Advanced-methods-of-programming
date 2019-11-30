package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Structures.*;
import Model.Values.StringValue;

import java.io.BufferedReader;

public class ForkStatement implements iStatement {
    private iStatement statement;

    public ForkStatement(iStatement statement){
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        PrgState newProgramState = new PrgState(new MyStack<>(), state.getSymbolTable().deepcopy(), state.getOut(), statement.deepcopy(), state.getFileTable(), state.getHeapTable());

       return newProgramState;
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new ForkStatement(statement.deepcopy());
    }

    @Override
    public String toString(){
        return "fork(" + statement.toString() + ")";
    }
}
