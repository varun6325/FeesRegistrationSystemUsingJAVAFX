package com.varun.controller;

import com.varun.App;
import com.varun.Utils;
import com.varun.fxmlmodels.CourseTableElem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class CourseDetailsSceneController {
    @FXML
    AnchorPane parentPane;
    @FXML private TableView<CourseTableElem> courseTableView;
    @FXML private TableColumn<CourseTableElem, Integer> courseId;
    @FXML private TableColumn<CourseTableElem, String> courseName;
    @FXML private TableColumn<CourseTableElem, String> courseDesc;
    @FXML private TableColumn<CourseTableElem, Double> courseFees;

    @FXML private void initialize(){
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseDesc.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        courseFees.setCellValueFactory(new PropertyValueFactory<>("courseFees"));
        courseTableView.setItems(Utils.getTestCourseList());
        courseTableView.setPlaceholder(new Label("No courses present in the system to display"));
        courseTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //courseTableView.setOnMouseClicked(this::handleMouseClickOnRow);
        courseTableView.setRowFactory(tv -> {
            TableRow<CourseTableElem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    CourseTableElem rowData = row.getItem();
                    System.out.println("Double click on: "+rowData.getCourseName());
                    try {
                        AddUpdateCourseSceneController.display(rowData);
                    }catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            });
            return row ;
        });
    }

    public static void display() throws IOException {
        Parent parent = Utils.loadFXML("CourseDetailsScene");
        App.setRoot(parent);

    }

}
