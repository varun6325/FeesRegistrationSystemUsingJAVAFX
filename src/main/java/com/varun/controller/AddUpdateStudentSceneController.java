package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;

public class AddUpdateStudentSceneController {

    @FXML private TitledPane registrationTitledPane;
    @FXML private ScrollPane scrollPane;
    //@FXML private AnchorPane anchorPane;
    @FXML
    private void initialize() throws IOException{
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        Separator separator = new Separator();
        vBox.getChildren().add(RegistrationDetailsController.getParentForRegistration(null));
        vBox.getChildren().add(separator);
        vBox.getChildren().add(RegistrationDetailsController.getParentForRegistration(null));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(vBox);
        registrationTitledPane.setContent(borderPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

    }

    public static void display(String previousSceneName, Scene previousScene) throws IOException {
        while( Utils.sceneStack.size() > 1 && !Utils.sceneStack.peek().getKey().equals(ParameterStrings.studentDetailsString))
            Utils.sceneStack.pop();
        Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
        Parent parent = Utils.loadFXML("AddUpdateStudentScene");
        Scene newScene = new Scene(parent);
        newScene.setRoot(parent);
        App.setScene(newScene);
    }
    @FXML
    private void onSubmitButtonClicked() throws IOException{
        Utils.homeButtonFunctionality();
    }

//    GridPane getGridPaneForRegistration(){
//        GridPane gridPane = new GridPane();
//        Label courseNameLabel = new Label("Course Name");
//        TextField courseNameTextField = new TextField("Test Course");
//        Label courseFeesLabel = new Label("Course Fees");
//        TextField courseFeesTextField = new TextField("2300.00");
//        Label discountLabel = new Label("Discount");
//        TextField discountTextField = new TextField("10");
//        Label feesToBePaidLabel = new Label("Fees To Be Paid");
//        TextField feesToBePaidTextField = new TextField("2000.00");
//        Label feesPaidLabel = new Label("Amount Already Paid");
//        TextField feesPaidTextField = new TextField("1500.00");
//        Label registrationDateLabel = new Label("Registration Date");
//        TextField registrationDateTextField = new TextField("Test Date");
//        gridPane.add(courseNameLabel, 0, 0, 1, 1);
//        gridPane.add(courseNameTextField, 1, 0, 1, 1);
//        gridPane.add(courseFeesLabel, 2, 0, 1, 1);
//        gridPane.add(courseFeesTextField, 3, 0, 1, 1);
//        gridPane.add(discountLabel, 0, 1, 1, 1);
//        gridPane.add(discountTextField, 1, 1, 1, 1);
//        gridPane.add(feesToBePaidLabel, 2, 1, 1, 1);
//        gridPane.add(feesToBePaidTextField, 3, 1, 1, 1);
//        gridPane.add(feesPaidLabel, 0, 2, 1, 1);
//        gridPane.add(feesPaidTextField, 1, 2, 1, 1);
//        gridPane.add(registrationDateLabel, 2, 2, 1, 1);
//        gridPane.add(registrationDateTextField, 3, 2, 1, 1);
//        return gridPane;
//    }

}
