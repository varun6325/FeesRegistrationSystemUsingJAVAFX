package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    public static void display() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("HomeScene" + ".fxml"));
        Parent parent = fxmlLoader.load();
        HomeSceneController homeSceneController =  fxmlLoader.getController();
        Scene newScene = new Scene(parent);
        newScene.getStylesheets().add(homeSceneController.getClass().getResource(ParameterStrings.cssResource).toExternalForm());

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
//    public void onAddStudentButtonClicked() throws IOException{
//        AddUpdateStudentSceneController.display(ParameterStrings.homeString, studentDetailsButton.getScene(), null);
//    }
}
