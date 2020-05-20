package com.varun.controller;

import com.varun.App;
import com.varun.Utils;
import com.varun.fxmlmodels.CourseTableElem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class AddUpdateCourseSceneController {
    @FXML private TextField courseNameTextField;
    @FXML private TextField courseIdTextField;
    @FXML private TextField courseDescTextField;
    @FXML private TextField courseFeesTextField;
    @FXML private Button submitButton;

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
    public static void display(CourseTableElem courseTableElem) throws IOException{
        AddUpdateCourseSceneController.courseTableElem = courseTableElem;
        Parent parent = Utils.loadFXML("AddUpdateCourseScene");
        App.setRoot(parent);
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
        CourseDetailsSceneController.display();
    }
}
