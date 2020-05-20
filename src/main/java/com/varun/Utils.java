package com.varun;

import com.varun.fxmlmodels.CourseTableElem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Stack;

public class Utils {
    private static Stack<Scene> sceneStack = new Stack<Scene>();
    static ObservableList<CourseTableElem> courses = null;
    public static ObservableList<CourseTableElem> getTestCourseList(){
        if(courses == null) {
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

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    static void insertToSceneStack(Scene scene){
        sceneStack.push(scene);
    }
    static Scene removeFromSceneStack(){
        return sceneStack.pop();
    }
}
