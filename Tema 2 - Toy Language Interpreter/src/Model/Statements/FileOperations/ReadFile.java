package Model.Statements.FileOperations;

import Model.Exceptions.MyException;
import Model.Expressions.iExpression;
import Model.PrgState;
import Model.Statements.iStatement;
import Model.Structures.FileTable;
import Model.Structures.MyIDictionary;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements iStatement {
    private iExpression expression;
    private String variableName;

    public ReadFile(iExpression expression, String variableName){
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary symbolTable = state.getSymbolTable();
        FileTable fileTable = (FileTable) state.getFileTable();
        Value value = expression.evaluate(symbolTable);

        if(!(value.getType().equals(new StringType()))){
            throw new MyException("Failed to read from file :'( \n STRING NOT VALID");
        }

        if(!fileTable.isDefined((StringValue) value)){
            throw new MyException("Failed to read from file :'( \n FILE NOT DEFINED");
        }

        BufferedReader currentReader = (BufferedReader) fileTable.get((StringValue) value);
        try{
            String lineRead = currentReader.readLine();
            IntValue intValue;
            if(lineRead.isEmpty()){
                intValue = (IntValue) new IntType().defaultValue();
            }else {
                intValue = new IntValue(Integer.parseInt(lineRead));
            }
            symbolTable.update(variableName, intValue);
        } catch (IOException e) {
            throw new MyException("Failed to read from file :'( \n COULDN'T READ LINES");
        }
        return state;
    }

    @Override
    public iStatement deepcopy() throws MyException {
        return new ReadFile(this.expression.deepcopy(), this.variableName);
    }
}
