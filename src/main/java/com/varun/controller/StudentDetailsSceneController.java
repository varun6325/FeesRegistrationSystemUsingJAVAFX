package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.db.managers.StudentManager;
import com.varun.fxmlmodels.CourseTableElem;
import com.varun.fxmlmodels.StudentTableElem;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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

    @FXML private void initialize(){
        studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentAge.setCellValueFactory(new PropertyValueFactory<>("studentAge"));
        studentEmail.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));
        studentPhNo.setCellValueFactory(new PropertyValueFactory<>("studentPhNo"));
        studentTableView.setItems(StudentManager.getStudentTableElemList());
        studentTableView.setPlaceholder(new Label("No student admitted into the system"));
        studentTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentTableView.setRowFactory(tv -> {
            TableRow<StudentTableElem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    StudentTableElem rowData = row.getItem();
                    System.out.println("Double click on: "+rowData.getStudentName());
                    try {
                        AddUpdateStudentSceneController.display(ParameterStrings.studentDetailsString, studentTableView.getScene(), rowData.getStudentId());
                    }catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            });
            return row ;
        });
    }

    public static void display(String previousSceneName, Scene previousScene) throws IOException {
        while(!Utils.sceneStack.empty() && !Utils.sceneStack.peek().getKey().equals(ParameterStrings.homeString))
            Utils.sceneStack.pop();
        if(Utils.sceneStack.empty() && previousSceneName.equals(ParameterStrings.homeString))
            Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
        Parent parent = Utils.loadFXML("StudentDetailsScene");
        Scene newScene = new Scene(parent);
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
        AddUpdateStudentSceneController.display(ParameterStrings.studentDetailsString, studentTableView.getScene(), -1);
    }
}
