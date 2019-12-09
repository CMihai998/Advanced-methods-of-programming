package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Structures.MyIDictionary;
import Model.Structures.iHeap;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class ReadHeapExpression implements iExpression {
    private iExpression expression;

    public ReadHeapExpression(iExpression expression){
        this.expression = expression;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, iHeap<Integer, Value> heapTable) throws MyException {
        Value value = expression.evaluate(table, heapTable);

        if(!(value instanceof RefValue)){
            throw new MyException("Failed to read from heap. Value not of reference type!");
        }

        Integer address = ((RefValue) value).getAddress();

        if(!heapTable.isDefined(address)){
            throw new MyException("Failed to read from heap. Value not assigned!");
        }

        return heapTable.getValue(address);
    }

    @Override
    public iExpression deepcopy() throws MyException {
        return new ReadHeapExpression(expression.deepcopy());
    }

    @Override
    public String toString(){
        return "Read(" + expression.toString() + ")";
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        Type type = expression.typeCheck(typeEnvironment);
        if(!(type instanceof RefType)) throw new MyException("The argument of Read Heap is not a RefType!");
        RefType reference = (RefType) type;
        return reference.getWrapped();
    }
}
