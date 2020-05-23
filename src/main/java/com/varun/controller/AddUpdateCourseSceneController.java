package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.fxmlmodels.CourseTableElem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.io.IOException;

public class AddUpdateCourseSceneController {
    @FXML private TextField courseNameTextField;
    @FXML private TextField courseIdTextField;
    @FXML private TextField courseDescTextField;
    @FXML private TextField courseFeesTextField;
    @FXML private Button submitButton;
    @FXML private  Button backButton;
    @FXML private  Button homeButton;
    private static CourseTableElem courseTableElem;
    @FXML private void initialize(){
        if(AddUpdateCourseSceneController.courseTableElem != null) {
            courseIdTextField.setText(String.valueOf(AddUpdateCourseSceneController.courseTableElem.getCourseId()));
            courseIdTextField.setEditable(false);
            courseNameTextField.setText(AddUpdateCourseSceneController.courseTableElem.getCourseName());
            courseDescTextField.setText(AddUpdateCourseSceneController.courseTableElem.getCourseDesc());
            courseFeesTextField.setText(String.valueOf(AddUpdateCourseSceneController.courseTableElem.getCourseFees()));
        }else{
            System.out.println("courseTableElem is null");
        }
    }
    public static void display(String previousSceneName, Scene previousScene, CourseTableElem courseTableElem) throws IOException{
        //This scene can only be called by Course Details Scene
        while( Utils.sceneStack.size() > 1 && !Utils.sceneStack.peek().getKey().equals(ParameterStrings.courseDetailsString))
            Utils.sceneStack.pop();
        Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
        AddUpdateCourseSceneController.courseTableElem = courseTableElem;
        Parent parent = Utils.loadFXML("AddUpdateCourseScene");
        Scene newScene = new Scene(parent);
        App.setScene(newScene);
    }
    @FXML
    private void onSubmitButtonClicked() throws IOException{
        ObservableList<CourseTableElem> courses = Utils.getTestCourseList();
        for(CourseTableElem elem : courses){
            if(elem.getCourseId() == courseTableElem.getCourseId()){
                elem.setCourseName(courseNameTextField.getText());
                elem.setCourseDesc(courseDescTextField.getText());
                elem.setCourseFees(Double.parseDouble(courseFeesTextField.getText()));
                break;
            }
        }
        CourseDetailsSceneController.display(ParameterStrings.addUpdateCourseString, submitButton.getScene());
    }
    @FXML
    private void onBackButtonClicked() throws IOException{
        Utils.backButtonFunctionality();
    }
    @FXML
    private void onHomeButtonClicked() throws IOException{
        Utils.homeButtonFunctionality();
    }
}
