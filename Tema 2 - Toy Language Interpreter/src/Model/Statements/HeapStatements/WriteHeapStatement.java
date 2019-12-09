package Model.Statements.HeapStatements;

import Model.Exceptions.MyException;
import Model.Expressions.ReadHeapExpression;
import Model.Expressions.iExpression;
import Model.PrgState;
import Model.Statements.iStatement;
import Model.Structures.MyIDictionary;
import Model.Structures.iHeap;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class WriteHeapStatement implements iStatement {
    private String variableName;
    private iExpression expression;

    public WriteHeapStatement(String variableName, iExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    public WriteHeapStatement(ReadHeapExpression a) {
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        iHeap<Integer, Value> heapTable = state.getHeapTable();

        if(!symbolTable.isDefined(variableName)){
            throw new MyException("Failed to write to heap. Variable name not defined!");
        }


        Value oldValue = symbolTable.get(variableName);

        if(!(oldValue.getType() instanceof RefType)){
            throw new MyException("Failed to write to heap. Type is not reference!");
        }

        if(!heapTable.isDefined(((RefValue) oldValue).getAddress())){
            throw new MyException("Failed to write to heap. Address not defined!");
        }

        Value newValue = expression.evaluate(symbolTable, heapTable);

        if(!newValue.getType().equals(((RefValue) oldValue).getLocationType())){
            throw new MyException("Failed to write to heap. Type of new value is wrong!");
        }

        heapTable.update(((RefValue) oldValue).getAddress(), newValue);
        return null;
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new WriteHeapStatement(this.variableName, this.expression.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        Type typeVariable = typeEnvironment.get(variableName);
        Type typeExpression = expression.typeCheck(typeEnvironment);
        if(!typeVariable.equals(new RefType(typeExpression))) throw new MyException("Assignment: right hand side and left hand side have different type");
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "(" + variableName + "->" + expression.toString() + ")";
    }
}
