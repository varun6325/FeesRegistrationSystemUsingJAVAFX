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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class AddUpdateCourseSceneController {
    @FXML private TextField courseNameTextField;
    //@FXML private TextField courseIdTextField;
    @FXML private Label courseIdLabel;
    @FXML private TextField courseDescTextField;
    @FXML private TextField courseFeesTextField;
    @FXML private Button submitButton;
    private CourseTableElem courseTableElem;


    private void setCourseTableElem(CourseTableElem courseTableElem) {
        this.courseTableElem = courseTableElem;
        if(courseTableElem != null) {
            courseIdLabel.setText(String.valueOf(courseTableElem.getCourseId()));
            courseNameTextField.setText(courseTableElem.getCourseName());
            courseDescTextField.setText(courseTableElem.getCourseDesc());
            courseFeesTextField.setText(String.valueOf(courseTableElem.getCourseFees()));
            //making fees textfield uneditable - This is because it has direct mapping with students via registrations.
            // If fees is updated for the course, it will impact the registration as for the already existing registrations the fees will be updated
            //taking those registrations to an inconsistent state
            courseFeesTextField.setEditable(false);
        }
    }

    @FXML private void initialize(){
        //courseIdTextField.setEditable(false);
        courseFeesTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            onCourseFeesTextChanged(oldValue, newValue);
        });
    }
    public static void display(String previousSceneName, Scene previousScene, CourseTableElem courseTableElem) throws IOException{
        //This scene can only be called by Course Details Scene
        if(previousSceneName.equals(ParameterStrings.courseDetailsString))
            Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AddUpdateCourseScene" + ".fxml"));
        Parent parent = fxmlLoader.load();
        AddUpdateCourseSceneController addUpdateCourseSceneController = fxmlLoader.getController();
        addUpdateCourseSceneController.setCourseTableElem(courseTableElem);
        Scene newScene = new Scene(parent);
        newScene.getStylesheets().add(addUpdateCourseSceneController.getClass().getResource(ParameterStrings.cssResource).toExternalForm());

        App.setScene(newScene);
    }
    @FXML
    private void onSubmitButtonClicked() throws IOException{
        //TODO: add validation of the text fields
        //TODO: add confirmation box
        CourseEntity courseEntity = new CourseEntity();
        String courseName = courseNameTextField.getText();

        if(courseTableElem == null || !courseTableElem.getCourseName().equals(courseName)){
            //if its a new course or we are modifying name of the course - we need to make sure that the course name does not exists
            if(CourseManager.checkCourseExists(courseName)) {
                Utils.showErrorDialog("Error Dialog", "", "Duplicate Course Name. This name already exists for some other course");
                return;
            }
        }
        if(courseName.equals("")) {
            Utils.showErrorDialog("Error Dialog", "", "Course Name cannot be empty");
            return;
        }
        String courseFees = courseFeesTextField.getText();
        if(courseFees.equals("")){
            Utils.showErrorDialog("Error Dialog", "", "Course Fees cannot be empty");
            return;
        }
        if(courseFees.equals("0.0")){
            Utils.showErrorDialog("Error Dialog", "", "Course Fees cannot be 0.0");
            return;
        }
        courseEntity.setCourseFees(new BigDecimal(Double.parseDouble(courseFees)));
        courseEntity.setCourseDesc(courseDescTextField.getText());
        courseEntity.setCourseName(courseName);
        if(courseTableElem != null){
            //updating a course
            courseEntity.setCourseId(courseTableElem.getCourseId());
            CourseEntity res = CourseManager.updateCourse(courseEntity);
            if(res == null) {
                System.out.println("update course failed : " + courseEntity.toString());
                Utils.showErrorDialog("ERROR", " Some major problem occurred", "If you keep on facing this error, then contact the admin");
            }
        }else{
            // adding a new course
            boolean ret = CourseManager.addCourse(courseEntity);
            if(!ret){
                System.out.println("add course failed : " + courseEntity.toString());
                Utils.showErrorDialog("ERROR", " Some major problem occurred", "If you keep on facing this error, then contact the admin");
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
    private void onCourseFeesTextChanged(String oldValue, String newValue){
        try {
            Double.parseDouble(newValue);
        }catch(NumberFormatException ex){
            System.out.println(ex);
            courseFeesTextField.setText("0.0");
        }
    }
}
