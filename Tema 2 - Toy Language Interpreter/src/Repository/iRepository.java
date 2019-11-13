package Repository;

import Model.Exceptions.MyException;
import Model.PrgState;

public interface iRepository {
    PrgState getCurrentProgramState();
    void addProgram(PrgState newProgram);
    void logProgramStateExecution() throws MyException;
}
