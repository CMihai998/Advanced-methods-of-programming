package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.iExpression;
import Model.PrgState;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIList;
import Model.Structures.iHeap;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStatement implements iStatement {
    private iExpression expression;

    public PrintStatement(iExpression expression){
        this.expression = expression;
    }

    public iExpression getExpression(){return expression;}

    public void setExpression(iExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString(){return "print(" + expression.toString() + ")";}

    @Override
    public iStatement deepcopy() throws MyException {
        return new PrintStatement(this.expression.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        Type type = expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> list = state.getOut();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        iHeap<Integer, Value> heapTable = state.getHeapTable();
        list.add(this.expression.evaluate(symbolTable, heapTable));
        return null;
    }

}
