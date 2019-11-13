package Controller;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statements.iStatement;
import Model.Structures.MyIStack;
import Repository.iRepository;

public class Controller {
    iRepository repository;

    public Controller(iRepository repository){
        this.repository = repository;
    }

    public PrgState oneStepEvaluation(PrgState state) throws MyException {
        MyIStack<iStatement> stack = state.getExecutionStack();
        if(stack.isEmpty())
            throw  new MyException("Stack is emptys :'(");
        iStatement currentStatement = stack.pop();
        if(currentStatement != null){
            this.repository.logProgramStateExecution();
            return currentStatement.execute(state);
        }
        else return null;
    }

    public PrgState allStepEvaluation() throws MyException{
        PrgState program = repository.getCurrentProgramState();
        while(!program.getExecutionStack().isEmpty()){
            oneStepEvaluation(program);
            System.out.println(repository.getCurrentProgramState().toString() + '\n');
        }
        return program;
    }

    public void  displayCurrentProgramState(){
        System.out.println(repository.getCurrentProgramState().toString() + '\n');
    }

    public void addProgram(PrgState newProgram){
        repository.addProgram(newProgram);
    }
}
