package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;

import java.io.IOException;

public class AddUpdateStudentSceneController {
    public static void display(String previousSceneName, Scene previousScene) throws IOException {
        while( Utils.sceneStack.size() > 1 && !Utils.sceneStack.peek().getKey().equals(ParameterStrings.studentDetailsString))
            Utils.sceneStack.pop();
        Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
        Parent parent = Utils.loadFXML("AddUpdateStudentScene");
        Scene newScene = new Scene(parent);
        newScene.setRoot(parent);
        App.setScene(newScene);
    }
}
