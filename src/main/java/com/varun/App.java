package com.varun;

import com.varun.controller.HomeSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(HomeSceneController.getParent());
        stage.setScene(scene);
        stage.show();
        App.stage = stage ;
        stage.sizeToScene();
    }

    static public void setScene(Scene scene){
        stage.setScene(scene);
        stage.sizeToScene();
    }
//    static public void setRoot(Parent parent){
//        scene.setRoot(parent);
//        App.stage.sizeToScene();
//    }
    public static void main(String[] args) {
        launch();
    }

}