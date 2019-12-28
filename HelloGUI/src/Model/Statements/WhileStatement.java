package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.iExpression;
import Model.PrgState;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIStack;
import Model.Structures.iHeap;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class WhileStatement implements iStatement {
    private iExpression expression;
    private iStatement statement;
    public WhileStatement(iExpression expression, iStatement statement){
        this.expression = expression;
        this.statement = statement;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<iStatement> stack = state.getExecutionStack();
        MyIDictionary symbolTable = state.getSymbolTable();
        iHeap<Integer, Value> heapTable = state.getHeapTable();
        Value value = expression.evaluate(symbolTable, heapTable);
        if(value.getType() != new BoolType()){
            if(!((BoolValue) value).getValue()){
                return state;
            }else{
                stack.push(this);
                stack.push(statement);
            }
        }else throw new MyException("Conditional expression is not a boolean value");
        return null;
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new WhileStatement(this.expression.deepcopy(), this.statement.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        Type typeExpression = expression.typeCheck(typeEnvironment);
        if(!typeExpression.equals(new IntType())) throw new MyException("The condition of WHILE is not a boolean value!");
        statement.typeCheck(typeEnvironment.deepcopy());
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "while(" + expression.toString() + ")execute(" + this.statement.toString() + ")";
    }
}
