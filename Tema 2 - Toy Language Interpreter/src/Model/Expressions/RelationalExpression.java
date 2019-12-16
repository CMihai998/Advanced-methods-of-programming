package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Structures.MyIDictionary;
import Model.Structures.iHeap;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class RelationalExpression implements iExpression{
    private iExpression expression1;
    private iExpression expression2;
    int operator;

    public RelationalExpression(iExpression expression1, iExpression expression2, int operator){
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, iHeap<Integer, Value> heapTable) throws MyException {
        Value value2;
        Value value1 = expression1.evaluate(table, heapTable);
        if(value1.getType().equals(new IntType())){
            value2 = expression2.evaluate(table, heapTable);
            if(value2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) value1;
                IntValue i2 = (IntValue) value2;

                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();

                if(operator == 1) return new BoolValue(n1 < n2 );
                else
                    if(operator == 2) return new BoolValue( n1 <= n2);
                    else
                        if(operator == 3) return new BoolValue(n1 == n2);
                        else
                            if(operator == 4) return new BoolValue(n1 != n2);
                            else
                                if(operator == 5) return new BoolValue(n1 > n2);
                                else
                                    if(operator == 6) return new BoolValue(n1 >= n2);
                                    else
                                        throw new MyException("Invalid relational operator");
            }else
            throw new MyException("Second operand is not integer");
        }else
            throw new MyException("First operand is not integer");
    }

    @Override
    public String toString(){
        if(operator == 1)
            return expression1.toString() + " < " + expression2.toString() + '\n';
        else if(operator == 2)
                return  expression1.toString() + " <= " + expression2.toString() + '\n';
            else if(operator == 3)
                    return expression1.toString() + " == " + expression2.toString() + '\n';
                else if(operator == 4)
                        return expression1.toString() + " != " + expression2.toString() + '\n';
                    else if(operator == 5)
                        return expression1.toString() + " > " + expression2.toString() + '\n';
        return expression1.toString() + " >= " + expression2.toString() + '\n';
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnvironment);
        type2 = expression2.typeCheck(typeEnvironment);
        if(!type1.equals(new IntType())) throw new MyException("First operand is not an integer!");
        if(!type2.equals(new IntType())) throw new MyException("Second operand is not an integer!");
        return new IntType();
    }

    @Override
    public iExpression deepcopy() throws MyException {
        return new RelationalExpression(this.expression1.deepcopy(), this.expression2.deepcopy(), this.operator);
    }
}
