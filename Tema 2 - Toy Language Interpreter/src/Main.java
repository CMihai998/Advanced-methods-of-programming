import Controller.Controller;
import Model.Exceptions.MyException;
import Repository.Repository;
import View.UserInterface;

public class Main {
    public static void main(String [] args) throws MyException {
       Repository repository = new Repository("log.txt");
       Controller controller = new Controller(repository);
       UserInterface userInterface = new UserInterface(controller);
       userInterface.start();
    }

}

