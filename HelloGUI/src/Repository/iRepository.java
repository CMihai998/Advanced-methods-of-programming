package Repository;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statements.iStatement;

import java.util.List;

public interface iRepository {
    PrgState getCurrentProgramState();
    void addProgram(PrgState newProgram);
    void logProgramStateExecution(PrgState state) throws MyException;
    List<PrgState> getProgramList();
    void setProgramStateList(List<PrgState> newList);
    void reset();
    iStatement getOriginalProgram();

}
