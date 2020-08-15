package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.db.managers.CourseManager;
import com.varun.fxmlmodels.CourseTableElem;
import javafx.collections.transformation.FilteredList;
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
    @FXML private ChoiceBox<String> searchChoiceBox;
    @FXML private TextField searchTextField;

    @FXML private void initialize(){
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseDesc.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        courseFees.setCellValueFactory(new PropertyValueFactory<>("courseFees"));
        FilteredList<CourseTableElem> flCourses = new FilteredList(CourseManager.getCourseTableElemList(), p -> true);

        courseTableView.setItems(flCourses);
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
                        System.out.println(ex);
                    }
                }
            });
            return row ;
        });
        searchChoiceBox.getItems().addAll("courseName", "courseDesc");// to add a new filter for search, add here
        searchChoiceBox.setValue("courseName");
        searchTextField.setPromptText("Search here!");
        searchTextField.setOnKeyReleased(keyEvent ->
        {
            if(!searchTextField.getText().isEmpty()) {
                switch (searchChoiceBox.getValue())//Switch on choiceBox value
                {
                    //all the choice box elements for which the searching needs to be implemented
                    case "courseName"://filter table by course name
                        flCourses.setPredicate(p -> {
                            if (p.getCourseName() != null)
                                return p.getCourseName().toLowerCase().contains(searchTextField.getText().toLowerCase().trim());
                            else
                                return false;
                        });
                        break;
                    case "courseDesc"://filter table by course description
                        flCourses.setPredicate(p -> {
                            if (p.getCourseDesc() != null)
                                return p.getCourseDesc().toLowerCase().contains(searchTextField.getText().toLowerCase().trim());
                            else
                                return false;
                        });
                        break;
                }
            }else
                flCourses.setPredicate(null);
        });
        searchChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
                searchTextField.setText("");
                flCourses.setPredicate(null);//This is same as saying flPerson.setPredicate(p->true);
            }
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
