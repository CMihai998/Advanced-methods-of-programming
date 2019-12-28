package Model.Statements;

import Model.Exceptions.MyException;
import Model.Expressions.iExpression;
import Model.PrgState;
import Model.Structures.MyIDictionary;
import Model.Structures.MyIStack;
import Model.Structures.iHeap;
import Model.Types.Type;
import Model.Values.Value;

public class AssignmentStatement implements iStatement {
    private String id;
    private iExpression expression;

    public AssignmentStatement(String id, iExpression expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        iHeap<Integer, Value> heapTable = state.getHeapTable();
        Value value = expression.evaluate(symbolTable, heapTable);
        if(symbolTable.isDefined(id)){
            Type typeId = symbolTable.get(id).getType();
            if(value.getType().equals(typeId)){
                symbolTable.update(id,value);
            }
            else throw new MyException("Declared type of variable " + id + " and type of the assigned expression do not match :'(");
        }
        else throw new MyException("The used variable " + id + " was not declared!");
        return null;
    }

    @Override
    public String toString(){
        return id + " = " + expression.toString();
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new AssignmentStatement(this.id, this.expression.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        Type typeVariable = typeEnvironment.get(id);
        Type typeExpression = expression.typeCheck(typeEnvironment);
        if(!typeVariable.equals(typeExpression)) throw new MyException("Assignment: right hand side and left hand side have different types!");
        return typeEnvironment;
    }
}
