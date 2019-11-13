package Model.Statements.FileOperations;

import Model.Exceptions.MyException;
import Model.Expressions.iExpression;
import Model.PrgState;
import Model.Statements.iStatement;
import Model.Structures.FileTable;
import Model.Structures.MyIDictionary;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements iStatement {
    private iExpression expression;

    public CloseRFile(iExpression expression){
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary symbolTable = state.getSymbolTable();
        FileTable fileTable = (FileTable) state.getFileTable();
        Value value = expression.evaluate(symbolTable);

        if(!(value.getType().equals(new StringType()))){
            throw new MyException("Failed to close the file :'( \n STRING NOT VALID");
        }

        if(!fileTable.isDefined((StringValue)value)){
            throw new MyException("Failed to close the file :'( \n FILE NOT DEFINED");
        }

        BufferedReader currentReader = (BufferedReader) fileTable.get((StringValue) value);
        try{
            currentReader.close();
        } catch (IOException e) {
            throw new MyException("Failed to close the file :'( \n READER COULD NOT BE CLOSED");
        }
        fileTable.remove((StringValue) value);
        return state;
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new CloseRFile(this.expression.deepcopy());
    }
}
