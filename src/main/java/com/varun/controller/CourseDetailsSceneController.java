package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.db.managers.CourseManager;
import com.varun.fxmlmodels.CourseTableElem;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.io.IOException;


public class CourseDetailsSceneController {
    @FXML
    AnchorPane parentPane;
    @FXML private TableView<CourseTableElem> courseTableView;
    @FXML private TableColumn<CourseTableElem, Integer> courseId;
    @FXML private TableColumn<CourseTableElem, String> courseName;
    @FXML private TableColumn<CourseTableElem, String> courseDesc;
    @FXML private TableColumn<CourseTableElem, Double> courseFees;

    @FXML private void initialize(){
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseDesc.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        courseFees.setCellValueFactory(new PropertyValueFactory<>("courseFees"));
        courseTableView.setItems(CourseManager.getCourseTableElemList());
        courseTableView.setPlaceholder(new Label("No courses present in the system to display"));
        courseTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //courseTableView.setOnMouseClicked(this::handleMouseClickOnRow);
        courseTableView.setRowFactory(tv -> {
            TableRow<CourseTableElem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    CourseTableElem rowData = row.getItem();
                    System.out.println("Double click on: "+rowData.getCourseName());
                    try {
                        AddUpdateCourseSceneController.display(ParameterStrings.courseDetailsString, courseTableView.getScene(), rowData);
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
        Parent parent = Utils.loadFXML("CourseDetailsScene");
        Scene newScene = new Scene(parent);
        newScene.setRoot(parent);
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
    private void onAddCourseButtonClicked() throws IOException{
        AddUpdateCourseSceneController.display(ParameterStrings.courseDetailsString, courseTableView.getScene(), null);
    }

}
