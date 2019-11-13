package Model.Statements;

import Model.Exceptions.MyException;
import Model.Structures.MyIStack;
import Model.PrgState;

public class CompoundStatement implements iStatement {
    private iStatement first;
    private iStatement second;

    public CompoundStatement(iStatement first, iStatement second){
        this.first = first;
        this.second = second;
    }

    public CompoundStatement(iStatement v) {

    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<iStatement> stack = state.getExecutionStack();
        stack.push(this.second);
        stack.push(this.first);
        return state;
    }

    @Override
    public String toString(){
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new CompoundStatement(this.first.deepcopy(), this.second.deepcopy());
    }

}
