package View;

import Controller.Controller;
import Model.Exceptions.MyException;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.Expressions.iExpression;
import Model.PrgState;
import Model.Statements.*;
import Model.Structures.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.Repository;
import Repository.iRepository;

import java.io.BufferedReader;
import java.util.Scanner;

public class UserInterface {
    private Controller controller;

    public UserInterface(){}

    public UserInterface(Controller controller){
        this.controller = controller;
    }

    public static void main(String[] args){
        try {
            iStatement example1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
            PrgState program1 = new PrgState(programOne().deepcopy());
            iRepository repository1 = new Repository("log1.txt");
            Controller controller1 = new Controller(repository1);
            controller1.addProgram(program1);


            iStatement example2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))), new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                    new PrintStatement(new VariableExpression("b"))))));
            PrgState program2 = new PrgState(programTwo().deepcopy());
            iRepository repository2 = new Repository("log2.txt");
            repository2.addProgram(program2);
            Controller controller2 = new Controller(repository2);
            controller2.addProgram(program2);

            iStatement example3 = null;
            PrgState program3 = new PrgState(example3);
            iRepository repository3 = new Repository("log3.txt");
            repository3.addProgram(program3);
            Controller controller3 = new Controller(repository3);

            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "Exit"));
            menu.addCommand(new RunExampleCommand("1", programOne().toString(), controller1));
            menu.addCommand(new RunExampleCommand("2", programTwo().toString(), controller2));
            //menu.addCommand(new RunExampleCommand("3", example3.toString(), controller3));
            menu.show();
        }catch (MyException exception){
            System.out.println(exception.getMessage());
        }
        //TODO comparators, files and more examples
    }


    public void start() throws MyException {
        while(true){
            System.out.println("Choose a program!");
            System.out.println("\t 1. int v; print(v)");
            System.out.println("\t 2. int a;int b; a=2+3*5;b=a+1;Print(b)");
            System.out.println("\t 3. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");

            Scanner scan = new Scanner(System.in);
            int result = scan.nextInt();
            iStatement expression = null;

            switch (result){
                case 1:
                    expression = programOne();
                    break;
                case 2:
                    expression = programTwo();
                    break;
                case 3:
                    expression = programThree();
                    break;
            }
            MyIStack<iStatement> stack = new MyStack<iStatement>();
            MyIDictionary<String, Value> symbolTable = new MyDictionary<String, Value>();
            MyIList<Value> list = new MyList<Value>();
            MyIDictionary<StringValue, BufferedReader> fileTable = new FileTable<StringValue, BufferedReader>();
            stack.push(expression);
            PrgState state = new PrgState(stack, symbolTable, list, expression, fileTable);

            this.controller.addProgram(state);

            System.out.println("Current program state:");
            controller.displayCurrentProgramState();

            while (true){
                System.out.println("1. One step evaluation");
                System.out.println("2. Full evaluation");

                int userInput = scan.nextInt();
                if(userInput == 1){
                    try{
                        controller.oneStepEvaluation(state);
                        controller.displayCurrentProgramState();
                    }catch (MyException error){
                        System.out.println(error.toString());
                        if(error.toString().equals("Model.Exceptions.myException: Stack is empty :'(")){
                            return;
                        }
                    }
                }else if(userInput == 2){
                    try{
                        controller.allStepEvaluation();
                    } catch (MyException error){
                        System.out.println(error.toString());
                        if(error.toString().equals("Model.Exceptions.myException: Stack is empty :'(")){
                            return;
                        }
                    }
                }
            }
        }
    }

    private static iStatement programOne(){
        return new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
    }

    private static iStatement programTwo() throws MyException {
        return new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new VariableDeclarationStatement("b", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new ValueExpression(new IntValue(2)), new ArithmeticExpression('*', new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))), new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+', new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                new PrintStatement(new VariableExpression("b"))))));
    }

    private iStatement programThree() {
        return null;
    }
}
