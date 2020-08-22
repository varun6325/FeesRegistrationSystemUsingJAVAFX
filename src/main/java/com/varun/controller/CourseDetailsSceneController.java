package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.db.managers.CourseManager;
import com.varun.db.managers.RegistrationManager;
import com.varun.fxmlmodels.CourseTableElem;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;


public class CourseDetailsSceneController {
    @FXML
    AnchorPane parentPane;
    @FXML private TableView<CourseTableElem> courseTableView;
    @FXML private TableColumn<CourseTableElem, Integer> courseId;
    @FXML private TableColumn<CourseTableElem, String> courseName;
    @FXML private TableColumn<CourseTableElem, String> courseDesc;
    @FXML private TableColumn<CourseTableElem, Double> courseFees;
    @FXML private ChoiceBox<String> searchChoiceBox;
    @FXML private TextField searchTextField;
    private FilteredList<CourseTableElem> flCourses;
    @FXML private void initialize(){
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseDesc.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        courseFees.setCellValueFactory(new PropertyValueFactory<>("courseFees"));
        flCourses = new FilteredList(CourseManager.getCourseTableElemList(), p -> true);

        courseTableView.setItems(flCourses);
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
                        AddUpdateCourseSceneController.display(ParameterStrings.courseDetailsString, courseTableView.getScene(), rowData);
                    }catch(IOException ex){
                        System.out.println(ex);
                    }
                }
            });
            return row ;
        });
        searchChoiceBox.getItems().addAll("course name", "course desc");// to add a new filter for search, add here
        searchChoiceBox.setValue("course name");
        searchTextField.setPromptText("Search here!");
        searchTextField.setOnKeyReleased(keyEvent ->
        {
            if(!searchTextField.getText().isEmpty()) {
                switch (searchChoiceBox.getValue())//Switch on choiceBox value
                {
                    //all the choice box elements for which the searching needs to be implemented
                    case "course name"://filter table by course name
                        flCourses.setPredicate(p -> {
                            if (p.getCourseName() != null)
                                return p.getCourseName().toLowerCase().contains(searchTextField.getText().toLowerCase().trim());
                            else
                                return false;
                        });
                        break;
                    case "course desc"://filter table by course description
                        flCourses.setPredicate(p -> {
                            if (p.getCourseDesc() != null)
                                return p.getCourseDesc().toLowerCase().contains(searchTextField.getText().toLowerCase().trim());
                            else
                                return false;
                        });
                        break;
                }
            }else
                flCourses.setPredicate(null);
        });
        searchChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
                searchTextField.setText("");
                flCourses.setPredicate(null);//This is same as saying flPerson.setPredicate(p->true);
            }
        });
    }

    public static void display(String previousSceneName, Scene previousScene) throws IOException {
        //This scene can be called either by home scene or after addn/updatn of a course ie AddUpdateCourseScene
        if(Utils.sceneStack.empty())
            Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
        else{
            while(!Utils.sceneStack.empty() && !Utils.sceneStack.peek().getKey().equals(ParameterStrings.homeString))
                Utils.sceneStack.pop();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("CourseDetailsScene" + ".fxml"));
        Parent parent = fxmlLoader.load();
        CourseDetailsSceneController courseDetailsSceneController =  fxmlLoader.getController();
        Scene newScene = new Scene(parent);
        newScene.setRoot(parent);
        URL url = courseDetailsSceneController.getClass().getResource(ParameterStrings.cssResource);
        String str = url.toExternalForm();
        newScene.getStylesheets().add(str);

        App.setScene(newScene);

    }
    @FXML
    private void onBackButtonClicked(){
        Utils.backButtonFunctionality();
    }
    @FXML
    private void onHomeButtonClicked() throws IOException{
        Utils.homeButtonFunctionality();
    }
    @FXML
    private void onAddCourseButtonClicked() throws IOException{
        AddUpdateCourseSceneController.display(ParameterStrings.courseDetailsString, courseTableView.getScene(), null);
    }
    @FXML
    private void onDeleteCourseButtonClicked() {
        CourseTableElem courseTableElem = courseTableView.getSelectionModel().getSelectedItem();
        if(courseTableElem != null) {
            if(Utils.showsConfirmDialog("", "Are you sure you want to delete this course ?")) {
            int ret = CourseManager.deleteCourseById(courseTableElem.getCourseId());
            if (ret == 1) {
                //flCourses.remove(courseTableElem);
                flCourses.getSource().remove(courseTableElem);
                courseTableView.setItems(flCourses);
            } else if (ret == 0)
                Utils.showErrorDialog("Error Dialog", "", "A registration already exists for this course. So, the course can't be deleted");
            else
                Utils.showErrorDialog("Error Dialog", "Contact the administrator", "Some error occurred. Can't delete this course. ");
            }
        }else
            Utils.showErrorDialog("Error Dialog", "", "No course selected");
    }

}
