package Model.Statements.HeapStatements;

import Model.Exceptions.MyException;
import Model.Expressions.iExpression;
import Model.PrgState;
import Model.Statements.VariableDeclarationStatement;
import Model.Statements.iStatement;
import Model.Structures.MyIDictionary;
import Model.Structures.iHeap;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class NewStatement implements iStatement {
    private String variableName;
    private iExpression expression;

    public NewStatement(String variableName, iExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary symbolTable = state.getSymbolTable();
        iHeap<Integer, Value> heapTable = state.getHeapTable();
        Value value = expression.evaluate(symbolTable, heapTable);

        if (!symbolTable.isDefined(variableName)) {
            throw new MyException("Failed to allocate. The variable " + variableName + " was not defined!");
        }

        if (!(((Value) symbolTable.get(variableName)).getType() instanceof RefType)) {
            throw new MyException("Failed to allocate. The type is not a reference!");
        }

        RefValue valueInSymbolTable = (RefValue) symbolTable.get(variableName);

        if (!value.getType().equals(valueInSymbolTable.getLocationType())) {
            throw new MyException("Failed to allocate. The variables are not of the same type!");
        }

        RefValue valueToPutInTable = new RefValue((Integer) heapTable.getCurrentFree(), value.getType());
        valueToPutInTable.setAddress((Integer) heapTable.getCurrentFree());
        symbolTable.update(variableName, valueToPutInTable);
        heapTable.update(value);
        return null;
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new NewStatement(variableName, expression.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        Type typeVariable = typeEnvironment.get(variableName);
        Type typeExpression = expression.typeCheck(typeEnvironment);
        if(!typeVariable.equals(new RefType(typeExpression))) throw new MyException("Assignment: right hand side and left hand side have different types");
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "New(" + variableName + "->" + expression.toString() + ")";
    }
}
