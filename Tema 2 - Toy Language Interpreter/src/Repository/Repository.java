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
            return programStateList.get(0);
        return new PrgState();
    }

    @Override
    public void addProgram(PrgState newProgram) {
        programStateList.add(newProgram);
    }

    @Override
    public void logProgramStateExecution(PrgState state) throws MyException {
        try(PrintWriter logFIle = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))){
            logFIle.print("Execution Stack: " + state.getExecutionStack().toString() + '\n');
            logFIle.print("Symbol table: " + state.getSymbolTable().toString() + '\n');
            logFIle.print("Out: " + state.getOut().toString() + '\n');
            logFIle.print("Heap table: " + state.getHeapTable() + '\n');
            logFIle.print("-------------------" + '\n');
        }catch (IOException error){
            throw new  MyException(error.toString());
        }
    }

    @Override
    public List<PrgState> getProgramList() {
        return programStateList;
    }

    @Override
    public void setProgramStateList(List<PrgState> newList) {
        this.programStateList = newList;
    }

    public void reset(){
        programStateList.forEach(program -> {
            try {
                program.reset();
            } catch (MyException e) {
                e.printStackTrace();
            }
        });
    }
}
