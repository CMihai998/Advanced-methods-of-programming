package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Structures.MyIDictionary;
import Model.Structures.iHeap;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExpression implements iExpression {
    private iExpression expression1;
    private iExpression expression2;
    int operator;

    public LogicExpression(iExpression expression1, iExpression expression2, int operator){
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, iHeap<Integer, Value> heapTable) throws MyException {
        Value value2;
        Value value1 = expression1.evaluate(table, heapTable);
        if(value1.getType().equals(new BoolType())){
            value2 = expression2.evaluate(table, heapTable);
            if(value2.getType().equals(new BoolType())){
                BoolType i1 = (BoolType) value1;
                BoolType i2 = (BoolType) value2;

                boolean n1,n2;
                n1 = i1.getValue();
                n2 = i2.getValue();

                if(operator == 1) return  new BoolValue(n1 && n2);
                else
                    if(operator == 2) return  new BoolValue(n1 || n2);
                    else throw new MyException("Invalid logical operator!!");
            }else
                throw new MyException("Second operand is not boolean");
        }else
            throw new MyException("First operand is not boolean");
    }

    @Override
    public String toString(){
        if(operator == 1)
            return expression1.toString() + " && " + expression2.toString() + '\n';
        return  expression1.toString() + " || " + expression2.toString() + '\n';
    }

    @Override
    public iExpression deepcopy() throws MyException {
        return new LogicExpression(this.expression1.deepcopy(), this.expression2.deepcopy(), this.operator);
    }
}
