package ViewFx;

import Controller.Controller;
import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statements.CompoundStatement;
import Model.Statements.iStatement;
import Model.Structures.FileTable;
import Model.Values.Value;
import Repository.Repository;
import Repository.iRepository;
import View.Command;
import View.ExitCommand;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ControllerFX {

    private iRepository repository;
    private Controller controller;
    private int activeProgram;

    @FXML
    private Button oneStepForAllButton;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<TableRow,String> heapAddressColumn;


    @FXML
    private TableColumn<TableRow,String> heapValueColumn;


    @FXML
    private TableColumn<TableRow,String> symbolNameColumn;


    @FXML
    private TableColumn<TableRow,String> symbolValueColumn;

    @FXML
    private ListView outList;

    @FXML
    private ListView listOfProgramStates;

    @FXML
    private ListView executableStack;

    @FXML
    private ListView fileTable;

    @FXML
    private TableView<TableRow> heapTable;

    @FXML
    private TableView<TableRow> symbolTable;

    @FXML
    private TextField numberOfProgramStates;

    @FXML
    public void oneStepEvaluation(ActionEvent event){
        try{
            controller.oneStepForAllPrograms(repository.getProgramList());
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        } catch (MyException e) {
            System.out.println(e.toString());
        }
        if(repository.getProgramList().size() == 0)
            return;
        updateData();
    }

    @FXML
    public void exit(ActionEvent event){
        Command exit = new ExitCommand("0", "Exit");
        exit.execute();
    }

    public void setProgram(iStatement expression) throws MyException{
        repository = new Repository("log.txt");
        controller = new Controller(repository);

        PrgState state = new PrgState(expression);

        listOfProgramStates.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String >)  (observable, oldValue, newValue) -> {
            // Your action here
            System.out.println("Selected program state: " + newValue);

            if(newValue==null) // this is for when nothing is selected
                return;

            int programNumber = Integer.parseInt(newValue);
            if(programNumber <= repository.getProgramList().size() && programNumber>0){
                System.out.println("Old program state: " + activeProgram + " new program state " + newValue);
                activeProgram = programNumber - 1;

                // this must be done otherwise i get an index error given by javafx when i try to clear the observablelist because you shouldn't do that with another event or smth like that? Point is it gets it done
                Platform.runLater(this::updateData);
            }else{}});


        heapAddressColumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("name"));

        heapValueColumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("value"));

        symbolNameColumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("name"));

        symbolValueColumn.setCellValueFactory(new PropertyValueFactory<TableRow,String>("value"));


        repository.addProgram(state);

        activeProgram=0;
    }

    @FXML
    private void updateData(){
        updateTotalProgramStates();
        updateProgramStates();
        updateSymbolTable();
        updateHeapTable();
        updateExecutionStack();
        updateFileTable();
        updateOutList();
    }

    public iStatement getOriginalProgram(){
        return controller.getOriginalProgram();
    }

    private void updateTotalProgramStates(){
        numberOfProgramStates.setText(Integer.toString(repository.getProgramList().size()));
    }

    private void   updateProgramStates(){
        listOfProgramStates.getItems().clear();

        List<String> temporaryList = (repository.getProgramList().stream())
                .map(state -> state.getId())
                .map(id -> id.toString())
                .collect(Collectors.toList());

        temporaryList.forEach(elem -> listOfProgramStates.getItems().add(elem));
    }

    private void updateSymbolTable(){
        HashMap<String, Value> hashSymbolTable = repository.getProgramList().get(activeProgram).getSymbolTable().getWrapped();
        Set<String> keys = hashSymbolTable.keySet();

        ObservableList<TableRow> newGUISymbolTable = FXCollections.observableList(keys.stream()
                .map(e -> new TableRow(e, hashSymbolTable.get(e).toString()))
                .collect(Collectors.toList()));

        symbolTable.getItems().clear();
        symbolTable.setItems(newGUISymbolTable);
    }

    private void updateHeapTable(){
        ConcurrentHashMap<Integer,Value> hashHeapTable = repository.getProgramList().get(activeProgram).getHeapTable().getWrapped();
        Set<Integer> keys = hashHeapTable.keySet();

        ObservableList<TableRow> newGUIHashTable = FXCollections.observableList(keys.stream()
                .map(e -> new TableRow(Integer.toString(e), hashHeapTable.get(e).toString()))
                .collect(Collectors.toList()));

        heapTable.getItems().clear();
        heapTable.setItems(newGUIHashTable);
    }

    private void updateExecutionStack(){
        executableStack.getItems().clear();

        Stack stack = repository.getProgramList().get(activeProgram).getExecutionStack().getWrapped();

        List<String> parseStack = processStatements(stack);

        parseStack.forEach(e -> executableStack.getItems().add(e));
    }

    private List<String> processStatements(Stack stack) {
        List<String> returnList = new LinkedList<>();
        while(!stack.isEmpty()){
            iStatement statement = (iStatement) stack.pop();
            if(statement instanceof CompoundStatement) {
                stack.push(((CompoundStatement) statement).getSecond());
                stack.push(((CompoundStatement) statement).getFirst());
            }else {
                returnList.add(statement.toString());
            }
        }
        return returnList;
    }

    private void updateFileTable(){
        fileTable.getItems().clear();

        List<String> temporaryList = ((FileTable)repository.getProgramList().get(activeProgram).getFileTable()).getStringValues();
        Collections.reverse(temporaryList);

        temporaryList.forEach(e -> fileTable.getItems().add(e));
    }

    private void updateOutList(){
        outList.getItems().clear();

        List<String> temporaryList = repository.getProgramList().get(activeProgram).getOut().getStreamOfElements()
                .map(e -> e.toString())
                .collect(Collectors.toList());

        Collections.reverse(temporaryList);

        temporaryList.forEach(e -> outList.getItems().add(e));
    }

    public static class TableRow {

        private String name,value;

        private TableRow(String name, String value){
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

