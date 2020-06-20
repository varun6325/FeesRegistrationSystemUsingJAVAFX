package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.db.managers.CourseManager;
import com.varun.db.models.CourseEntity;
import com.varun.fxmlmodels.CourseTableElem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.io.IOException;
import java.math.BigDecimal;

public class AddUpdateCourseSceneController {
    @FXML private TextField courseNameTextField;
    @FXML private TextField courseIdTextField;
    @FXML private TextField courseDescTextField;
    @FXML private TextField courseFeesTextField;
    @FXML private Button submitButton;
    private CourseTableElem courseTableElem;


    public void setCourseTableElem(CourseTableElem courseTableElem) {
        this.courseTableElem = courseTableElem;
        if(courseTableElem != null) {
            courseIdTextField.setText(String.valueOf(courseTableElem.getCourseId()));
            courseNameTextField.setText(courseTableElem.getCourseName());
            courseDescTextField.setText(courseTableElem.getCourseDesc());
            courseFeesTextField.setText(String.valueOf(courseTableElem.getCourseFees()));
        }
    }

    @FXML private void initialize(){
        courseIdTextField.setEditable(false);
    }
    public static void display(String previousSceneName, Scene previousScene, CourseTableElem courseTableElem) throws IOException{
        //This scene can only be called by Course Details Scene
        while( Utils.sceneStack.size() > 1 && !Utils.sceneStack.peek().getKey().equals(ParameterStrings.courseDetailsString))
            Utils.sceneStack.pop();
        Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AddUpdateCourseScene" + ".fxml"));
        Parent parent = fxmlLoader.load();
        ((AddUpdateCourseSceneController)(fxmlLoader.getController())).setCourseTableElem(courseTableElem);
        Scene newScene = new Scene(parent);
        App.setScene(newScene);
    }
    @FXML
    private void onSubmitButtonClicked() throws IOException{
        //TODO: add validation of the text fields
        //TODO: add confirmation box
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseFees(new BigDecimal(Double.parseDouble(courseFeesTextField.getText())));
        courseEntity.setCourseDesc(courseDescTextField.getText());
        courseEntity.setCourseName(courseNameTextField.getText());
        if(courseTableElem != null){
            courseEntity.setCourseId(courseTableElem.getCourseId());
            CourseEntity res = CourseManager.updateCourse(courseEntity);
            if(res == null) {
                System.out.println("update course failed : " + courseEntity.toString());
                //TODO:show an alert box for error
            }
        }else{
            boolean ret = CourseManager.addCourse(courseEntity);
            if(!ret){
                System.out.println("add course failed : " + courseEntity.toString());
                //TODO : show an alert for error
            }

        }
        CourseDetailsSceneController.display(ParameterStrings.addUpdateCourseString, submitButton.getScene());
    }
    @FXML
    private void onBackButtonClicked(){
        Utils.backButtonFunctionality();
    }
    @FXML
    private void onHomeButtonClicked() throws IOException{
        Utils.homeButtonFunctionality();
    }
}
