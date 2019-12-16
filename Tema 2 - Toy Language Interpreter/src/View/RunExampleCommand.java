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
            System.out.println("\nCHECKING TYPES...");
            controller.getOriginalProgram().typeCheck(environmentVariables);
            System.out.println("TYPES MATCH... \n\t STARTING EXECUTION... \n");
            controller.allStep();
        } catch (MyException | InterruptedException e) {
            System.out.println("EXECUTION STOPPED! TYPES DO NOT MATCH \n");
            System.out.println(e.getMessage());
        }
    }
}
