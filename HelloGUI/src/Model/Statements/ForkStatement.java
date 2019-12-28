package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Structures.*;
import Model.Types.Type;
import Model.Values.StringValue;

import java.io.BufferedReader;

public class ForkStatement implements iStatement {
    private iStatement statement;

    public ForkStatement(iStatement statement){
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        PrgState newProgramState = new PrgState(new MyStack<>(), state.getSymbolTable().deepcopy(), state.getOut(), statement, state.getFileTable(), state.getHeapTable());
       return newProgramState;
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new ForkStatement(statement.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        statement.typeCheck(typeEnvironment.deepcopy());
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "fork(" + statement.toString() + ")";
    }
}
