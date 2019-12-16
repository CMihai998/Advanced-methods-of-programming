package Model.Statements.FileOperations;

import Model.Exceptions.MyException;
import Model.Expressions.iExpression;
import Model.PrgState;
import Model.Statements.iStatement;
import Model.Structures.FileTable;
import Model.Structures.MyIDictionary;
import Model.Structures.iHeap;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements iStatement {
    private iExpression expression;

    public OpenRFile(iExpression expression){
        this.expression = expression;
    }



    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary symbolTable = state.getSymbolTable();
        iHeap<Integer, Value> heapTable = state.getHeapTable();
        FileTable fileTable = (FileTable) state.getFileTable();
        Value value = expression.evaluate(symbolTable, heapTable);

        if(!(value.getType().equals(new StringType()))){
            throw new MyException("Failed to open the file :'( \n STRING NOT VALID");
        }

        String actualString = ((StringValue) value).getValue();

        if(fileTable.isDefined((StringValue) value)){
            throw new MyException("Failed to open the file :'( \n FILE ALREADY DEFINED");
        }

        try{
            BufferedReader reader = new BufferedReader(new FileReader(actualString));
            fileTable.update((StringValue) value, reader);
        } catch (FileNotFoundException e) {
            throw new MyException("Open file failed :'( \n IO Exception");
        } catch (IOException e) {
            throw new MyException("Open file failed :'( \n IO Exception");
        }
        return null;
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new OpenRFile(this.expression.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws MyException {
        Type expressionType = expression.typeCheck(typeEnvironment);
        if(!expressionType.equals(new StringType())) throw new MyException("The parameter of Open File is not of StringType!");
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "open(" + expression.toString() + ")";
    }
}
