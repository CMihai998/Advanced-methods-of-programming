package View;

import Controller.Controller;
import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Statements.FileOperations.CloseRFile;
import Model.Statements.FileOperations.OpenRFile;
import Model.Statements.FileOperations.ReadFile;
import Model.Statements.HeapStatements.NewStatement;
import Model.Statements.HeapStatements.WriteHeapStatement;
import Model.Structures.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.Repository;
import Repository.iRepository;

import java.io.BufferedReader;
import java.util.Scanner;

public class Interpreter {
    private Controller controller;

    public Interpreter(){}

    public Interpreter(Controller controller){
        this.controller = controller;
    }

    public static void main(String[] args){
        try {
            iStatement example1 = programOne();
            PrgState program1 = new PrgState(example1);
            iRepository repository1 = new Repository("log1.txt");
            Controller controller1 = new Controller(repository1);
            controller1.addProgram(program1);


            iStatement example2 = programTwo();

            PrgState program2 = new PrgState(example2);
            iRepository repository2 = new Repository("log2.txt");
            repository2.addProgram(program2);
            Controller controller2 = new Controller(repository2);
            controller2.addProgram(program2);

            iStatement example3 = programThree();
            PrgState program3 = new PrgState(example3);
            iRepository repository3 = new Repository("log3.txt");
            repository3.addProgram(program3);
            Controller controller3 = new Controller(repository3);
            controller3.addProgram(program3);

            iStatement example4 = programFour();
            PrgState program4 = new PrgState(example4);
            iRepository repository4 = new Repository("log4.txt");
            repository4.addProgram(program4);
            Controller controller4 = new Controller(repository4);
            controller4.addProgram(program4);

            iStatement example5 = programFive();
            PrgState program5 = new PrgState(example5);
            iRepository repository5 = new Repository("log5.txt");
            repository5.addProgram(program5);
            Controller controller5 = new Controller(repository5);
            controller5.addProgram(program5);

            iStatement example6 = programSix();
            PrgState program6 = new PrgState(example6);
            iRepository repository6 = new Repository("log6.txt");
            repository6.addProgram(program6);
            Controller controller6 = new Controller(repository6);
            controller6.addProgram(program6);

            iStatement example7 = programSeven();
            PrgState program7 = new PrgState(example7);
            iRepository repository7 = new Repository("log7.txt");
            repository7.addProgram(program7);
            Controller controller7 = new Controller(repository7);
            controller7.addProgram(program7);

            iStatement example8 = programEight();
            PrgState program8 = new PrgState(example8);
            iRepository repository8 = new Repository("log8.txt");
            repository8.addProgram(program8);
            Controller controller8 = new Controller(repository8);
            controller8.addProgram(program8);

            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "Exit"));
            menu.addCommand(new RunExampleCommand("1", example1.toString(), controller1));
            menu.addCommand(new RunExampleCommand("2", example2.toString(), controller2));
            menu.addCommand(new RunExampleCommand("3", example3.toString(), controller3));
            menu.addCommand(new RunExampleCommand("4", example4.toString(), controller4));
            menu.addCommand(new RunExampleCommand("5", example5.toString(), controller5));
            menu.addCommand(new RunExampleCommand("6", example6.toString(), controller6));
            menu.addCommand(new RunExampleCommand("7", example7.toString(), controller7));
            menu.addCommand(new RunExampleCommand("8", example8.toString(), controller8));
            menu.show();
        }catch (MyException exception){
            System.out.println(exception.getMessage());
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

    private static iStatement programThree() {
        return new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()), new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true)))
                , new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));
    }

    private static iStatement programFour(){
        return new CompoundStatement(
                new VariableDeclarationStatement("varf",new StringType()),new CompoundStatement(
                new AssignmentStatement("varf",new ValueExpression(new StringValue("test.in"))),new CompoundStatement(
                new OpenRFile(new VariableExpression("varf")),new CompoundStatement(
                new VariableDeclarationStatement("varc",new IntType()),new CompoundStatement(
                new ReadFile(new VariableExpression("varf"),"varc"),new CompoundStatement(
                new PrintStatement(new VariableExpression("varc")),new CompoundStatement(
                new ReadFile(new VariableExpression("varf"),"varc") ,new CompoundStatement(new PrintStatement(new VariableExpression("varc")),new CloseRFile(new VariableExpression("varf"))))))))));
    }

    private static iStatement programFive() throws MyException {
        return new CompoundStatement(
                new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                        new WhileStatement(
                                new RelationalExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0)),5),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement( "v",new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1))))))));
    }

    private static iStatement programSix() throws MyException {
        return new CompoundStatement(
                new VariableDeclarationStatement("v",new RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), new CompoundStatement(
                                new VariableDeclarationStatement("a",new RefType(new RefType(new  IntType()))), new CompoundStatement(
                                new NewStatement("a",new VariableExpression("v")),new CompoundStatement(
                                new NewStatement("v",new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression('+' ,new ReadHeapExpression(new ReadHeapExpression( new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));
    }

  private static iStatement programSeven() throws MyException {
        return new CompoundStatement(
                new VariableDeclarationStatement("v",new RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), new CompoundStatement(
                                new VariableDeclarationStatement("a",new RefType(new RefType(new  IntType()))), new CompoundStatement(
                                new NewStatement("a",new VariableExpression("v")),new CompoundStatement(
                                new WriteHeapStatement("v",new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression('+' ,new ReadHeapExpression(new ReadHeapExpression( new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));
  }

  private static iStatement programEight(){
        iStatement forked =   new CompoundStatement(new WriteHeapStatement("a",new ValueExpression(new IntValue(30))),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))));
      return new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
              new CompoundStatement(new VariableDeclarationStatement("a",new RefType(new IntType())),
                      new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                              new CompoundStatement(new NewStatement("a",new ValueExpression(new IntValue(22))),
                                      new CompoundStatement(new ForkStatement(forked),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))
                      )));
  }

  private static iStatement programOpt(){
      iStatement t1=new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new VariableDeclarationStatement("a", new RefType(new IntType())));
      iStatement t2=new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))), new NewStatement("a", new ValueExpression(new IntValue(22))));
      iStatement t3=new CompoundStatement(t1,t2);
      iStatement t4=new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
              new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                      new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))));
      iStatement t5=new CompoundStatement(t3 ,new ForkStatement(t4));
      iStatement t6=new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))));
      iStatement ex10=new CompoundStatement(t5, t6);
      return ex10;
  }

  //TODO add typeEnvironment field to prgState(? or not)
    //TODO add typeCheck in execute before run with a locally created dictionary
}
