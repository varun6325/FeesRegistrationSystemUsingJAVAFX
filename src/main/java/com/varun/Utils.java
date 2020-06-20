package com.varun;

import com.varun.controller.HomeSceneController;
import com.varun.fxmlmodels.CourseTableElem;
import com.varun.fxmlmodels.InstallmentTableElem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Date;
import java.util.Stack;

public class Utils {
    public static Stack<Pair<String, Scene>> sceneStack = new Stack<>();
    static ObservableList<CourseTableElem> courses = null;
    static ObservableList<InstallmentTableElem> installmentTableElems = null;

    public static ObservableList<CourseTableElem> getTestCourseList() {
        if (courses == null) {
            courses = FXCollections.observableArrayList();
            courses.add(new CourseTableElem(1, "course 1", "desc 1", 2500.0));
            courses.add(new CourseTableElem(2, "course 2", "desc 2", 3500.0));
            courses.add(new CourseTableElem(3, "course 3", "desc 3", 1500.0));
            courses.add(new CourseTableElem(4, "course 4", "desc 4", 2000.0));
            courses.add(new CourseTableElem(5, "course 5", "desc 5", 1000.0));
            courses.add(new CourseTableElem(6, "course 6", "desc 6", 2300.0));
        }
        return courses;
    }
    public static ObservableList<InstallmentTableElem> getTestInstallmentTableElem() {
        if (installmentTableElems == null) {
            installmentTableElems = FXCollections.observableArrayList();
            installmentTableElems.add(new InstallmentTableElem(1, 1000.0, true, new Date()));
            installmentTableElems.add(new InstallmentTableElem(1, 1500.0, false, new Date()));
            installmentTableElems.add(new InstallmentTableElem(1, 1200.0, true, new Date()));
            installmentTableElems.add(new InstallmentTableElem(1, 1100.0, true, new Date()));
            installmentTableElems.add(new InstallmentTableElem(1, 1300.0, false, new Date()));
            System.out.println(installmentTableElems.size());
        }
        return installmentTableElems;
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

//    public static void insertToSceneStack(Scene scene) {
//        sceneStack.push(scene);
//    }
//
//    public static Scene removeFromSceneStack() {
//        return sceneStack.pop();
//    }

    public static void backButtonFunctionality() {
        Pair pair = Utils.sceneStack.pop();
        App.setScene((Scene)pair.getValue());
    }

    public static void homeButtonFunctionality() throws IOException{
        Utils.sceneStack.clear();
        HomeSceneController.display();
    }
}
