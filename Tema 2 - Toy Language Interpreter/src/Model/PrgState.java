package Model;

import Model.Exceptions.MyException;
import Model.Statements.iStatement;
import Model.Structures.*;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import javax.xml.stream.FactoryConfigurationError;
import java.io.BufferedReader;

public class PrgState {
    private MyIStack<iStatement> executionStack;
    private MyIDictionary<String, Value> symbolTable;
    private MyIList<Value> out;
    private iStatement originalProgram;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private iHeap<Integer, Value> heapTable;
    public int id;
    private static int lastAssignedId = 1;

    public static int getNewId(){
        lastAssignedId++;
        return lastAssignedId;
    }

    public PrgState(MyIStack<iStatement> executionStack, MyIDictionary<String, Model.Values.Value> symbolTable, MyIList<Model.Values.Value> out, iStatement originalProgram, MyIDictionary<StringValue, BufferedReader> fileTable, iHeap<Integer, Value> heapTable) throws MyException {
        this.executionStack = executionStack;
        this. symbolTable = symbolTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.id = getNewId();
        this.executionStack.push(originalProgram.deepcopy());
    }

    public PrgState(MyIStack<iStatement> executionStack, MyIDictionary<String, Model.Values.Value> symbolTable, MyIList<Model.Values.Value> out, iStatement originalProgram, MyIDictionary<StringValue, BufferedReader> fileTable, iHeap<Integer, Value> heapTable, int id) throws MyException {
        this.executionStack = executionStack;
        this. symbolTable = symbolTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.id = id;
        this.executionStack.push(originalProgram.deepcopy());
    }

    public PrgState(iStatement originalProgram) throws MyException {
        this.executionStack = new MyStack<iStatement>();
        this.symbolTable = new MyDictionary<String, Value>();
        this.out = new MyList<Value>();
        this.originalProgram = originalProgram.deepcopy();
        this.executionStack.push(originalProgram);
        this.fileTable = new FileTable<StringValue, BufferedReader>();
        this.heapTable = new HeapTable<Integer, Value>();
        this.id = getNewId();
    }

    public PrgState() {
        this.executionStack = new MyStack<iStatement>();
        this.symbolTable = new MyDictionary<String, Value>();
        this.out = new MyList<Value>();
        this.id = getNewId();
    }

    public void reset() throws MyException {
        this.executionStack = new MyStack<iStatement>();
        this.symbolTable = new MyDictionary<String, Value>();
        this.out = new MyList<Value>();
        this.executionStack.push(this.originalProgram.deepcopy());
        this.fileTable = new FileTable<StringValue, BufferedReader>();
        this.heapTable = new HeapTable<Integer, Value>();
    }

    public MyIStack<iStatement> getExecutionStack() {
        return executionStack;
    }

    public MyIDictionary<String, Value> getSymbolTable(){
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

    public iHeap<Integer, Value> getHeapTable() {
        return heapTable;
    }

    public int getId() {
        return id;
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

    public void setHeapTable(iHeap<Integer, Value> heapTable) {
        this.heapTable = heapTable;
    }

    @Override
    public String toString(){
        return "ID:" + this.id + '\n' +
                "Executable Stack: " + executionStack.toString() + '\n' +
                "Symbol Table: " + symbolTable.toString() + '\n' +
                "Out list: " + out.toString() + '\n' +
                "Heap: " + heapTable.toString() + '\n';
    }

    /*public PrgState deepcopy() throws MyException {
        return new PrgState(this.executionStack, this.symbolTable.deepcopy(), this.out, this.originalProgram.deepcopy(), this.fileTable.deepcopy(), this.heapTable);
    }*/

    public Boolean isNotCompleted(){
        return !executionStack.isEmpty();
    }

    public PrgState oneStepExecution() throws MyException {
        if(executionStack.isEmpty()) throw new MyException("Execution stack is empty! :'( " + id);

        iStatement currentStatement = executionStack.pop();

        return currentStatement.execute(this);

    }

}
