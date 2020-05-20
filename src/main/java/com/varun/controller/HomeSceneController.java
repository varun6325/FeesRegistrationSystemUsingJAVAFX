package com.varun.controller;

import com.varun.App;
import com.varun.Utils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
        App.setRoot(parent);
    }
    public void onCourseDetailsButtonClicked() throws IOException{
        ////CourseDetailsSceneController courseDetailsSceneController = new CourseDetailsSceneController();
        CourseDetailsSceneController.display();
    }
    public void onStudentDetailsButtonClicked() throws IOException{
        StudentDetailsSceneController.display();
    }
    public void onNotificationButtonClicked() throws IOException{
        NotificationSceneController.display();
    }

}
