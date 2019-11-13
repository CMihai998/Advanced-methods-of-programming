package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Structures.MyIDictionary;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Values.BoolValue;
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
    public Value evaluate(MyIDictionary<String, Value> table) throws MyException {
        Value value2;
        Value value1 = expression1.evaluate(table);
        if(value1.getType().equals(new IntType())){
            value2 = expression2.evaluate(table);
            if(value2.getType().equals(new IntType())){
                IntType i1 = (IntType) value1;
                IntType i2 = (IntType) value2;

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
    public iExpression deepcopy() throws MyException {
        return new RelationalExpression(this.expression1.deepcopy(), this.expression2.deepcopy(), this.operator);
    }
}
