package Model.Expressions;

import Model.Exceptions.MyException;
import Model.Structures.MyIDictionary;
import Model.Structures.iHeap;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.Value;


public class ArithmeticExpression implements iExpression {
    private iExpression e1, e2;
    private int operator;

    public ArithmeticExpression(){}

    public ArithmeticExpression(char operator, iExpression e1, iExpression e2) throws MyException {
        if(operator == '+')
            this.operator = 1;
        else if(operator == '-')
            this.operator = 2;
        else if(operator == '*')
            this.operator = 3;
        else if(operator == '/')
            this.operator = 4;
        else
            throw new MyException("Invalid operator");
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, iHeap<Integer, Value> heapTable) throws MyException {
        Value v1, v2;
        v1 = e1.evaluate(table, heapTable);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.evaluate(table, heapTable);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = ((IntValue) v1).getValue();
                n2 = ((IntValue) v2).getValue();
                if (operator == 1)
                    return new IntValue(n1 + n2);
                if (operator == 2)
                    return new IntValue(n1 - n2);
                if (operator == 3)
                    return new IntValue(n1 * n2);
                if (operator == 4)
                    if (n2 == 0) throw new MyException("Division by zero");
                    else
                        return new IntValue(n1 / n2);
            } else
                throw new MyException("Second operand is not an integer");
        } else
            throw new MyException("First operand is not an integer");

        return null;
    }

    @Override
    public String toString() {
        if (operator == 1)
            return e1.toString() + " + " + e2.toString();
        if (operator == 2)
            return e1.toString() + " - " + e2.toString();
        if (operator == 3)
            return e1.toString() + " * " + e2.toString();
        if (operator == 4)
            return e1.toString() + " / " + e2.toString();
        return null;
    }

    @Override
    public iExpression deepcopy() throws MyException {
        if(this.operator == 1)
            return new ArithmeticExpression('+', this.e1.deepcopy(), this.e2.deepcopy());
        if(this.operator == 2)
            return new ArithmeticExpression('-', this.e1.deepcopy(), this.e2.deepcopy());
        if(this.operator == 3)
            return new ArithmeticExpression('*', this.e1.deepcopy(), this.e2.deepcopy());
        if(this.operator == 4)
            return new ArithmeticExpression('/', this.e1.deepcopy(), this.e2.deepcopy());
        return new ArithmeticExpression();
    }
}
