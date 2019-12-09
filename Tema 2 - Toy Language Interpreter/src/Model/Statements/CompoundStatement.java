package Model.Statements;

import Model.Exceptions.MyException;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIStack;
import Model.PrgState;
import Model.Types.Type;

public class CompoundStatement implements iStatement {
    private iStatement first;
    private iStatement second;

    public CompoundStatement(iStatement first, iStatement second){
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<iStatement> stack = state.getExecutionStack();
        stack.push(this.second);
        stack.push(this.first);
        return null;
    }

    @Override
    public String toString(){
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new CompoundStatement(this.first.deepcopy(), this.second.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnvironment));
    }

}
