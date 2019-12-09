package View;

import Controller.Controller;
import Model.Exceptions.MyException;
import Model.Structures.MyDictionary;
import Model.Structures.MyIDictionary;
import Model.Types.Type;
import Model.Values.Value;

public class RunExampleCommand extends Command {
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller){
        super(key,description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try{
            MyIDictionary<String, Type> environmentVariables = new MyDictionary<String, Type>();
            controller.getOriginalProgram().typeCheck(environmentVariables);
            controller.allStep();
        } catch (MyException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
