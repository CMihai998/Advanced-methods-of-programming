package View;

import Controller.Controller;
import Model.Exceptions.MyException;

public class RunExampleCommand extends Command {
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller){
        super(key,description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try{
            controller.allStepEvaluation();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}
