package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.db.managers.StudentManager;
import com.varun.db.models.StudentEntity;
import com.varun.fxmlmodels.CourseTableElem;
import com.varun.fxmlmodels.StudentTableElem;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.IOException;

public class StudentDetailsSceneController {

    @FXML
    AnchorPane parentPane;
    @FXML private TableView<StudentTableElem> studentTableView;
    @FXML private TableColumn<StudentTableElem, Integer> studentId;
    @FXML private TableColumn<StudentTableElem, String> studentName;
    @FXML private TableColumn<StudentTableElem, Integer> studentAge;
    @FXML private TableColumn<StudentTableElem, String> studentPhNo;
    @FXML private TableColumn<StudentTableElem, String> studentEmail;
    @FXML private ChoiceBox<String> searchChoiceBox;
    @FXML private TextField searchTextField;

    @FXML private void initialize(){
        studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentAge.setCellValueFactory(new PropertyValueFactory<>("studentAge"));
        studentAge.setCellFactory(new Callback<TableColumn<StudentTableElem, Integer>, TableCell<StudentTableElem, Integer>>() {
            @Override
            public TableCell call(TableColumn tableColumn) {
                return new TableCell<String, Integer>(){
                    @Override
                    protected void updateItem(Integer integer, boolean b) {
                        if (integer != null) {
                            super.setText(integer < 0?"":integer.toString());
                        }
                    }
                };
            }
        });
        studentEmail.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));
        studentPhNo.setCellValueFactory(new PropertyValueFactory<>("studentPhNo"));
        FilteredList<StudentTableElem> flStudents = new FilteredList(StudentManager.getStudentTableElemList(), p -> true);
        studentTableView.setItems(flStudents);
        studentTableView.setPlaceholder(new Label("No student admitted into the system"));
        studentTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentTableView.setRowFactory(tv -> {
            TableRow<StudentTableElem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    StudentTableElem rowData = row.getItem();
                    System.out.println("Double click on: "+rowData.getStudentName());
                    try {
                        //registrations will be captured from the db later. they are lazily initialized
                        StudentEntity studentEntity = StudentManager.getStudentWithoutRegistrationsFromId(rowData.getStudentId());
                        AddUpdateStudentSceneController.display(ParameterStrings.studentDetailsString, studentTableView.getScene(), studentEntity);
                    }catch(IOException ex){
                        System.out.println(ex);
                    }
                }
            });
            return row ;
        });

        searchChoiceBox.getItems().addAll("student name", "student email");// to add a new filter for search, add here
        searchChoiceBox.setValue("student name");
        searchTextField.setPromptText("Search here!");
        searchTextField.setOnKeyReleased(keyEvent ->
        {
            if(!searchTextField.getText().isEmpty()) {
                switch (searchChoiceBox.getValue())//Switch on choiceBox value
                {
                    //all the choice box elements for which the searching needs to be implemented
                    case "student name":
                        flStudents.setPredicate(p -> {
                            //filter table by first name
                            if (p.getStudentName() != null)
                                return p.getStudentName().toLowerCase().contains(searchTextField.getText().toLowerCase().trim());
                            else
                                return false;
                        });
                        break;
                    case "student email":
                        //filter table by email
                        flStudents.setPredicate(p -> {
                            if (p.getStudentEmail() != null)
                                return p.getStudentEmail().toLowerCase().contains(searchTextField.getText().toLowerCase().trim());
                            else
                                return false;
                        });
                        break;
                }
            }else
                flStudents.setPredicate(null);

        });
        searchChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
                searchTextField.setText("");
                flStudents.setPredicate(null);//This is same as saying flPerson.setPredicate(p->true);
            }
        });
    }

    public static void display(String previousSceneName, Scene previousScene) throws IOException {
        //This scene can be called either by home scene or after addn/updatn of a student ie AddUpdateStudentScene
        if(Utils.sceneStack.empty())
            Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
        else{
            while(!Utils.sceneStack.empty() && !Utils.sceneStack.peek().getKey().equals(ParameterStrings.homeString))
                Utils.sceneStack.pop();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("StudentDetailsScene" + ".fxml"));
        Parent parent = fxmlLoader.load();
        StudentDetailsSceneController studentDetailsSceneController =  fxmlLoader.getController();
        Scene newScene = new Scene(parent);
        newScene.getStylesheets().add(studentDetailsSceneController.getClass().getResource(ParameterStrings.cssResource).toExternalForm());

        App.setScene(newScene);
    }

    @FXML
    private void onBackButtonClicked(){
        Utils.backButtonFunctionality();
    }
    @FXML
    private void onHomeButtonClicked() throws IOException{
        Utils.homeButtonFunctionality();
    }
    @FXML
    private void onAddStudentButtonClicked() throws IOException{
        AddUpdateStudentSceneController.display(ParameterStrings.studentDetailsString, studentTableView.getScene(), null);
    }
}
