package Controller;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statements.iStatement;
import Model.Structures.MyIStack;
import Repository.iRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private iRepository repository;
    private MyGarbageCollector garbageCollector;
    private ExecutorService executor;

    public Controller(iRepository repository){
        this.repository = repository;
        this.garbageCollector = new MyGarbageCollector();
    }

    public void oneStepForAllPrograms(List<PrgState> programs) throws MyException, InterruptedException {
        programs.forEach(program -> {
            try {
                repository.logProgramStateExecution(program);

            } catch (MyException e) {
                System.out.println("One step failed 401 " + e.toString());
            }
        });

        //prepare list of callables
        List<Callable<PrgState>> callList = programs.stream()
                .map((PrgState program) -> (Callable<PrgState>)() -> {return  program.oneStepExecution();})
                .collect(Collectors.toList());

        //start execution of callables
        List<PrgState> newProgramList = null;
        try {
            newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println("One step failed 402 " + e.toString());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull) // keeps only not null values
                    .collect(Collectors.toList());
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }

        //add new threads to existing ones
        programs.addAll(newProgramList);

        programs.forEach(program -> {
            try {
                repository.logProgramStateExecution(program);
                System.out.println(program.toString() + '\n');
            } catch (MyException e) {
                System.out.println("One step failed 403 " + e.toString());;
            }
        });
        repository.setProgramStateList(programs);
    }

    public void allStep() throws InterruptedException, MyException {
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> programList = removeCompletedProgram(repository.getProgramList());
        while(programList.size() > 0){
            repository.getProgramList().stream().forEach(program -> program.getHeapTable().setContent(
                    garbageCollector.safeGarbageCollector(
                            garbageCollector.addIndirections(garbageCollector.getAddressFromTables(repository.getProgramList()),program.getHeapTable()),
                            program.getHeapTable())));
            oneStepForAllPrograms(programList);
            programList = removeCompletedProgram(repository.getProgramList());
        }
        executor.shutdownNow();
        repository.setProgramStateList(programList);
        repository.reset();
    }


    public void addProgram(PrgState newProgram){
        repository.addProgram(newProgram);
    }

    public List<PrgState> removeCompletedProgram(List<PrgState> inProgress){
        return inProgress.stream()
                .filter(program -> program.isNotCompleted())
                .collect(Collectors.toList());
    }

    public PrgState oldAllStepEvaluation() throws MyException{
        PrgState program = repository.getCurrentProgramState();
        while(!program.getExecutionStack().isEmpty()) {
            //oneStepEvaluation(program);
            program.getHeapTable().setContent(
                    garbageCollector.safeGarbageCollector(
                            garbageCollector.addIndirections(garbageCollector.getAddressFromTables(repository.getProgramList()),program.getHeapTable()),
                            program.getHeapTable()));

            System.out.println(repository.getCurrentProgramState().toString() + '\n');
        }
        program.reset();
        return program;
    }

    public PrgState oldOneStepEvaluation(PrgState state) throws MyException {
        MyIStack<iStatement> stack = state.getExecutionStack();
        if(stack.isEmpty())
            throw  new MyException("Stack is emptys :'(");
        iStatement currentStatement = stack.pop();
        if(currentStatement != null){
            this.repository.logProgramStateExecution(state);
            return currentStatement.execute(state);
        }
        else return null;
    }
}
