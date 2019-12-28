package ViewFx;

import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.Statements.*;
import Model.Statements.FileOperations.CloseRFile;
import Model.Statements.FileOperations.OpenRFile;
import Model.Statements.FileOperations.ReadFile;
import Model.Statements.HeapStatements.NewStatement;
import Model.Statements.HeapStatements.WriteHeapStatement;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        iStatement expression = null;

        List<String> choices = new ArrayList<>();
        choices.add("int v; v = 2; print(v);");
        choices.add("int a; int b; a = 2 + 3 * 5; b = a + 1; print(b);");
        choices.add("bool a; int v; a = true; (If a Then v = 2; Else v =3;); print(v);");
        choices.add("string varf; varf = \"test.in\"; openRFile(varf); int varc; readFile(varf, varc); print(varc); readFile(varf, varc); print(varc); closeRFile(varf);");
        choices.add("int v; v = 4; (while (v > 0) print(v); v = v - 1 ); print(v);");
        choices.add("Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(rH(v)); print(rH(rH(a)) + 5);");
        choices.add("Ref int v; new(v, 20); print(rH(v)); wH(v,30); print(rH(v) + 5);");
        choices.add("int v; Ref int a; v = 10; new(a, 22); fork(wH(a, 30); v =3 2; print(v); print(rH(a))); print(v); print(rH(a));");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("int v; v = 2; print(v);", choices);
        dialog.setTitle("Program state picker");
        dialog.setHeaderText("Woah! Such nice Very Choice Dialog");
        dialog.setContentText("Choose a program to run: ");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            switch (result.get()){
                case "int v; v = 2; print(v);":
                    expression = programOne();
                    break;
                case "int a; int b; a = 2 + 3 * 5; b = a + 1; print(b);":
                    expression = programTwo();
                    break;
                case "bool a; int v; a = true; (If a Then v = 2; Else v =3;); print(v);":
                    expression = programThree();
                    break;
                case "string varf; varf = \"test.in\"; openRFile(varf); int varc; readFile(varf, varc); print(varc); readFile(varf, varc); print(varc); closeRFile(varf);":
                    expression = programFour();
                    break;
                case "int v; v = 4; (while (v > 0) print(v); v = v - 1 ); print(v);":
                    expression = programFive();
                    break;
                case "Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(rH(v)); print(rH(rH(a)) + 5);":
                    expression = programSix();
                    break;
                case "Ref int v; new(v, 20); print(rH(v)); wH(v,30); print(rH(v) + 5);":
                    expression = programSeven();
                    break;
                case "int v; Ref int a; v = 10; new(a, 22); fork(wH(a, 30); v =3 2; print(v); print(rH(a))); print(v); print(rH(a));":
                    expression = programEight();
                    break;
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();

        ControllerFX controllerFX = fxmlLoader.getController();
        controllerFX.setProgram(expression);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
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
                                        new CompoundStatement(new ForkStatement(forked), new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))
                        )));
    }
}
