package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeSceneController {
    @FXML private Button notificationsButton;
    @FXML private Button courseDetailsButton;
    @FXML private Button studentDetailsButton;

    @FXML private void initialize(){

    }

    public static Parent getParent() throws IOException{
        Parent parent = Utils.loadFXML("HomeScene");
        return parent;
    }
    public static void display() throws IOException {
        Parent parent = Utils.loadFXML("HomeScene");
        Scene newScene = new Scene(parent);
        App.setScene(newScene);
//        App.setRoot(parent);
    }
    public void onCourseDetailsButtonClicked() throws IOException{
        ////CourseDetailsSceneController courseDetailsSceneController = new CourseDetailsSceneController();
        CourseDetailsSceneController.display(ParameterStrings.homeString,courseDetailsButton.getScene());
    }
    public void onStudentDetailsButtonClicked() throws IOException{
        StudentDetailsSceneController.display(ParameterStrings.homeString, notificationsButton.getScene());
    }
    public void onNotificationButtonClicked() throws IOException{
        NotificationSceneController.display(ParameterStrings.homeString, studentDetailsButton.getScene());
    }

}
