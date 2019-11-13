package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIStack;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

public class VariableDeclarationStatement implements iStatement {
    private String name;
    private Type type;

    public VariableDeclarationStatement(String name, Type type){
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<iStatement> executionStack = state.getExecutionStack();
        MyIDictionary symbolTable = state.getSymbolTable();
        if(symbolTable.isDefined(name)){
            throw new MyException("Variable already declared!");
        }else{
            if(type instanceof IntType){
                symbolTable.update(name, new IntValue());
            }
            else if(type instanceof BoolValue){
                symbolTable.update(name, new BoolValue());
            }
            else if(type instanceof StringValue){
                symbolTable.update(name, new StringValue());
            }
        }
        return state;
    }

    @Override
    public String toString(){
        return name + " " + type.toString();
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new VariableDeclarationStatement(this.name, this.type.deepcopy());
    }
}
