package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.db.DBUtils;
import com.varun.db.managers.RegistrationManager;
import com.varun.db.managers.StudentManager;
import com.varun.db.models.RegistrationEntity;
import com.varun.db.models.StudentEntity;
import com.varun.fxmlmodels.RegistrationTableElem;
import com.varun.fxmlmodels.StudentTableElem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.hibernate.LazyInitializationException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AddUpdateStudentSceneController {

    private StudentEntity studentEntity;
    @FXML TableView registrationTableView;
    @FXML private ScrollPane scrollPane;
    @FXML private Label studentIdLabel;
    @FXML private TextField studentFNameTextField, studentMNameTextField, studentLNameTextField;
    @FXML private TextField studentPhNoTextField, studentAddressTextField, studentEmailTextField;
    @FXML private DatePicker studentDateOfBirthDatePicker;
    @FXML private Button addRegistrationButton, submitButton;
    @FXML private VBox mainVBox;
    @FXML private TableColumn<RegistrationTableElem, Integer> registrationIdCol;
    @FXML private TableColumn<RegistrationTableElem, String> courseNameCol;
    @FXML private TableColumn<RegistrationTableElem, Double> courseFeesCol;
    @FXML private TableColumn<RegistrationTableElem, Double> registrationDiscountCol;
    @FXML private TableColumn<RegistrationTableElem, Double> registrationAmountCol;
    @FXML private TableColumn<RegistrationTableElem, Double> registrationAmountPaidCol;
    @FXML private TableColumn<RegistrationTableElem, Date> registrationDateCol;
    //@FXML private AnchorPane anchorPane;
    @FXML
    private void initialize() throws IOException{
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        registrationTableView.setPlaceholder(new Label("No existing registrations"));
        registrationTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        registrationIdCol.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseFeesCol.setCellValueFactory(new PropertyValueFactory<>("courseFees"));
        registrationDiscountCol.setCellValueFactory(new PropertyValueFactory<>("registrationDiscount"));
        registrationAmountCol.setCellValueFactory(new PropertyValueFactory<>("registrationAmount"));
        registrationAmountPaidCol.setCellValueFactory(new PropertyValueFactory<>("registrationAmountPaid"));
        registrationDateCol.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        registrationTableView.setRowFactory(tv -> {
            TableRow<RegistrationTableElem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    RegistrationTableElem rowData = row.getItem();
                    System.out.println("Double click on Registration : "+rowData.getRegistrationId());
                    try {
                        RegistrationEntity registrationEntity = RegistrationManager.getRegistrationById(rowData.getRegistrationId());
                        AddUpdateRegistrationSceneController.display(ParameterStrings.addUpdateStudentString, registrationTableView.getScene(), studentEntity, registrationEntity);
                    }catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            });
            return row ;
        });
    }

    private void fillScene() throws IOException{
        if(studentEntity != null){
            //student already exists and is here to be modified
            studentIdLabel.setText(Integer.toString(studentEntity.getStudentId()));
            studentFNameTextField.setText(studentEntity.getStudentFName());
            studentMNameTextField.setText(studentEntity.getStudentMName());
            studentLNameTextField.setText(studentEntity.getStudentLName());
            studentAddressTextField.setText(studentEntity.getStudentAddress());
            LocalDate localDate = Utils.getLocalDateFromString(studentEntity.getStudentDateOfBirth().toString());
            studentDateOfBirthDatePicker.setValue(localDate);
            studentPhNoTextField.setText(studentEntity.getStudentPhNo());
            studentEmailTextField.setText(studentEntity.getStudentEmail());
            List<RegistrationEntity> registrationEntities;
            Iterator<RegistrationEntity> registrationEntityIterator;
            try {
                registrationEntities = (List) studentEntity.getRegistrationsByStudentId();
                registrationEntityIterator = registrationEntities.iterator();
            }catch(LazyInitializationException ex){
                System.out.println("Exception raised : " + ex.getMessage());
                studentEntity = StudentManager.getStudentByEntityWithRegistrations(studentEntity);
                registrationEntities = (List) studentEntity.getRegistrationsByStudentId();
                registrationEntityIterator = registrationEntities.iterator();
            }
            ObservableList<RegistrationTableElem> registrationTableElems = FXCollections.observableArrayList();
            while(registrationEntityIterator.hasNext()){
                RegistrationTableElem registrationTableElem = DBUtils.getRegistrationTableElemFromRegistrationEntity(registrationEntityIterator.next());
                registrationTableElems.add(registrationTableElem);
            }
            registrationTableView.setItems(registrationTableElems);
        }else{
            // new student, so don't show addRegistration button and registrations table view.
            mainVBox.getChildren().remove(addRegistrationButton);
            mainVBox.getChildren().remove(registrationTableView);
        }
    }

    public static AddUpdateStudentSceneController display(String previousSceneName, Scene previousScene, StudentEntity studentEntity) throws IOException {
        while( Utils.sceneStack.size() > 1 && !Utils.sceneStack.peek().getKey().equals(ParameterStrings.studentDetailsString))
            Utils.sceneStack.pop();
        Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AddUpdateStudentScene" + ".fxml"));
        Parent parent = fxmlLoader.load();
        AddUpdateStudentSceneController addUpdateStudentSceneController = (AddUpdateStudentSceneController)(fxmlLoader.getController());
        addUpdateStudentSceneController.setStudentEntity(studentEntity);
        addUpdateStudentSceneController.fillScene();
        Scene newScene = new Scene(parent);
        newScene.setRoot(parent);
        App.setScene(newScene);
        return addUpdateStudentSceneController;
    }

    //this function will only be called when a student already exists in the db
    @FXML
    private void onAddRegistrationButtonClicked() throws IOException{
        AddUpdateRegistrationSceneController.display(ParameterStrings.addUpdateStudentString, registrationTableView.getScene(), studentEntity,null);
    }
    @FXML
    private void onSubmitButtonClicked() throws IOException{
        //TODO: add validation of the text fields
        //TODO: add confirmation box
        System.out.println("onSubmit button clicked");
        if(studentEntity == null)
            studentEntity = new StudentEntity(); // student needs to be inserted - so create a new object
        studentEntity.setStudentFName(studentFNameTextField.getText());
        studentEntity.setStudentMName(studentMNameTextField.getText());
        studentEntity.setStudentLName(studentLNameTextField.getText());
        java.sql.Date sDate = Utils.getSqlDateFromLocalDate(studentDateOfBirthDatePicker.getValue());
        studentEntity.setStudentDateOfBirth(sDate);
        studentEntity.setStudentAddress(studentAddressTextField.getText());
        studentEntity.setStudentEmail(studentEmailTextField.getText());
        studentEntity.setStudentPhNo(studentPhNoTextField.getText());
        if(studentEntity == null){
            //inserting new student
            System.out.println("adding student");
            boolean ret = StudentManager.addStudent(studentEntity);
            if(!ret){
                System.out.println("insert student failed : " + studentEntity.toString());
            }
        }else{
            //updating new student
            System.out.println("updating student");
            StudentEntity ret = StudentManager.updateStudent(studentEntity);
            if(ret == null)
                System.out.println("update student failed : " + studentEntity.toString());

        }
        StudentDetailsSceneController.display(ParameterStrings.addUpdateStudentString, submitButton.getScene());
    }

    @FXML
    private void onBackButtonClicked(){
        Utils.backButtonFunctionality();
    }
    @FXML
    private void onHomeButtonClicked() throws IOException{
        Utils.homeButtonFunctionality();
    }

    public void setStudentEntity(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
    }
}
