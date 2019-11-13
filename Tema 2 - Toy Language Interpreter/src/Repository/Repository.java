package Repository;

import Model.Exceptions.MyException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Repository implements iRepository {
    private List<PrgState> programStateList;
    private String logFilePath;

    public Repository(String logFilePath){
        this.programStateList = new LinkedList<PrgState>();
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCurrentProgramState() {
        if(!programStateList.isEmpty())
            return programStateList.get(0); //TODO change this
        return new PrgState();
    }

    @Override
    public void addProgram(PrgState newProgram) {
        programStateList.add(newProgram);
    }

    @Override
    public void logProgramStateExecution() throws MyException {
        try(PrintWriter logFIle = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))){
            logFIle.print("Execution Stack: " + programStateList.get(0).getExecutionStack().toString() + '\n');
            logFIle.print("Symbol table: " + programStateList.get(0).getSymbolTable().toString() + '\n');
            logFIle.print("Out: " + programStateList.get(0).getOut().toString() + '\n');
            logFIle.print("-------------------" + '\n');
        }catch (IOException error){
            throw new  MyException(error.toString());
        }
    }
}
