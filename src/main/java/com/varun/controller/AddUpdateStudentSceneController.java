package com.varun.controller;

import com.varun.App;
import com.varun.Utils;
import javafx.scene.Parent;

import java.io.IOException;

public class AddUpdateStudentSceneController {
    public static void display() throws IOException {
        Parent parent = Utils.loadFXML("AddUpdateStudentScene");
        App.setRoot(parent);
    }
}
