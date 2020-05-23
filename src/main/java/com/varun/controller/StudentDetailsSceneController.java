package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.fxmlmodels.CourseTableElem;
import com.varun.fxmlmodels.StudentTableElem;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    @FXML private TableColumn<StudentTableElem, String> studentAddress;

    @FXML private void initialize(){
        studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentAge.setCellValueFactory(new PropertyValueFactory<>("studentAge"));
        studentAddress.setCellValueFactory(new PropertyValueFactory<>("studentAddress"));
        //studentTableView.setItems(Utils.getTestCourseList());
        studentTableView.setPlaceholder(new Label("No student admitted into the system"));
        studentTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
}
