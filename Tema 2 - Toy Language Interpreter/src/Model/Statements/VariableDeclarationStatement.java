package Model.Statements;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIStack;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.StringType;
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
            symbolTable.update(name, type.defaultValue());
        }
        return null;
    }

    @Override
    public String toString(){
        return name + " " + type.toString();
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new VariableDeclarationStatement(this.name, this.type.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        typeEnvironment.update(name, type);
        return typeEnvironment;
    }
}

