package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.iExpression;
import Model.PrgState;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIStack;
import Model.Structures.iHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStatement implements iStatement {
    private iExpression expression;
    private iStatement thenStatement;
    private iStatement elseStatement;

    public IfStatement(){}

    public IfStatement(iExpression expression, iStatement thenStatement, iStatement elseStatement){
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString(){
        return "IF(" + expression.toString() + ")THEN(" + thenStatement.toString() + ")ELSE(" + elseStatement.toString() + ")";
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new IfStatement(this.expression.deepcopy(), this.thenStatement.deepcopy(), this.elseStatement.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        Type typeExpression = expression.typeCheck(typeEnvironment);
        if(!typeExpression.equals(new BoolType())) throw new MyException("The condition of IF is not a boolean value!");

        MyIDictionary<String, Type> thenEnvironment, elseEnvironment;
        thenStatement.typeCheck(typeEnvironment.deepcopy());
        elseStatement.typeCheck(typeEnvironment.deepcopy());

        return typeEnvironment;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<iStatement> executionStack = state.getExecutionStack();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        iHeap<Integer, Value> heapTable = state.getHeapTable();
        Value condition = expression.evaluate(symbolTable, heapTable);
        if(condition.getType() instanceof BoolType){
           BoolValue actualValue = (BoolValue) condition;
           if(actualValue.getValue()){
               executionStack.push(thenStatement);
           }else{
               executionStack.push(elseStatement);
           }
        } else {
            throw new MyException("This is not a logical expression!");
        }
        return null;
    }
}
