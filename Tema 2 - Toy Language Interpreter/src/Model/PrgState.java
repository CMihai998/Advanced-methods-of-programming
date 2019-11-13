package Model;

import Model.Statements.iStatement;
import Model.Structures.*;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<iStatement> executionStack;
    private MyIDictionary<String, Value> symbolTable;
    private MyIList<Value> out;
    private iStatement originalProgram;
    private MyIDictionary<StringValue, BufferedReader> fileTable;

    public PrgState(MyIStack<iStatement> executionStack, MyIDictionary<String, Model.Values.Value> symbolTable, MyIList<Model.Values.Value> out, iStatement originalProgram, MyIDictionary<StringValue, BufferedReader> fileTable){
        this.executionStack = executionStack;
        this. symbolTable = symbolTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
    }

    public PrgState(iStatement originalProgram){
        this.executionStack = new MyStack<iStatement>();
        this.symbolTable = new MyDictionary<String, Value>();
        this.out = new MyList<Value>();
        this.originalProgram = originalProgram;
        this.executionStack.push(originalProgram);
        this.fileTable = new MyDictionary<StringValue, BufferedReader>();
    }

    public PrgState() {
        this.executionStack = new MyStack<iStatement>();
        this.symbolTable = new MyDictionary<String, Value>();
        this.out = new MyList<Value>();
    }

    public MyIStack<iStatement> getExecutionStack() {
        return executionStack;
    }

    public MyIDictionary getSymbolTable(){
        return symbolTable;
    }

    public MyIList<Model.Values.Value> getOut() {
        return out;
    }

    public iStatement getOriginalProgram() {
        return originalProgram;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setExecutionStack(MyIStack<iStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymbolTable(MyIDictionary<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void setOut(MyIList<Model.Values.Value> out) {
        this.out = out;
    }

    public void setOriginalProgram(iStatement originalProgram) {
        this.originalProgram = originalProgram;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    @Override
    public String toString(){
        return "Executable Stack: " + executionStack.toString() + '\n' +
                "Symbol Table: " + symbolTable.toString() + '\n' +
                "Out list: " + out.toString() + '\n';
    }

    public PrgState deepcopy(){
        return new PrgState(this.executionStack, this.symbolTable, this.out, this.originalProgram, (MyDictionary<StringValue, BufferedReader>) this.fileTable);
    }
}
