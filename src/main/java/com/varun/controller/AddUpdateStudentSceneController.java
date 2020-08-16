package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.db.DBUtils;
import com.varun.db.managers.StudentManager;
import com.varun.db.models.RegistrationEntity;
import com.varun.db.models.StudentEntity;
import com.varun.fxmlmodels.RegistrationTableElem;
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
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class AddUpdateStudentSceneController {

    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
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
//                        RegistrationEntity registrationEntity = RegistrationManager.getRegistrationById(rowData.getRegistrationId());
                        RegistrationEntity registrationEntity = null;
                        for(RegistrationEntity reg : studentEntity.getRegistrationsByStudentId()){
                            if(reg.getRegistrationId() == rowData.getRegistrationId())
                                registrationEntity = reg;
                        }
                        //NOTE: at this point registration entity would be without the installations info - they would be needed to load lazily when required
                        if(registrationEntity != null)
                            AddUpdateRegistrationSceneController.display(ParameterStrings.addUpdateStudentString, registrationTableView.getScene(), studentEntity, registrationEntity);
                        else
                            System.out.println("Registration Entity can't be null");
                    }catch(IOException ex){
                        System.out.println(ex);
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
            if(studentEntity.getStudentDateOfBirth() != null){
                String dob = studentEntity.getStudentDateOfBirth().toString();
                LocalDate localDate = Utils.getLocalDateFromString(dob);
                studentDateOfBirthDatePicker.setValue(localDate);
            }
            studentPhNoTextField.setText(studentEntity.getStudentPhNo());
            studentEmailTextField.setText(studentEntity.getStudentEmail());
            List<RegistrationEntity> registrationEntities;
            Iterator<RegistrationEntity> registrationEntityIterator;
            // always get fresh registrations for the db for the student
            studentEntity = StudentManager.getStudentWithRegistrationsFromEntity(studentEntity);
            registrationEntities = (List) studentEntity.getRegistrationsByStudentId();
            registrationEntityIterator = registrationEntities.iterator();

            ObservableList<RegistrationTableElem> registrationTableElems = FXCollections.observableArrayList();
            while(registrationEntityIterator.hasNext()){
                RegistrationTableElem registrationTableElem = DBUtils.getRegistrationTableElemFromRegistrationEntity(registrationEntityIterator.next(), null);
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
        AddUpdateStudentSceneController addUpdateStudentSceneController = fxmlLoader.getController();
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

        String fName = studentFNameTextField.getText();
        String mName = studentMNameTextField.getText();
        String lName = studentLNameTextField.getText();
        LocalDate dob = studentDateOfBirthDatePicker.getValue();
        java.sql.Date sqlDOB = null;
        String phNo = studentPhNoTextField.getText();
        String email = studentEmailTextField.getText();

        if(fName == null || fName.equals("")) {
            Utils.showErrorDialog("Error Dialog", null, "first name can't be null");
            return;
        }
        if(!Utils.regexMatch(fName,"^[a-z ,.'-]+$", true )) {
            Utils.showErrorDialog("Error Dialog", null, "first name can only have the following characters A-Z, a-z, comma, dot, hyphen, inverted comma");
            return;
        }
        if(!mName.equals("") && !Utils.regexMatch(mName,"^[a-z ,.'-]*$", true )) {
            Utils.showErrorDialog("Error Dialog", null, "middle name can only have the following characters A-Z, a-z, comma, dot, hyphen, inverted comma");
            return;
        }
        if(!lName.equals("") && !Utils.regexMatch(lName,"^[a-z ,.'-]*$", true )) {
            Utils.showErrorDialog("Error Dialog", null, "last name can only have the following characters A-Z, a-z, comma, dot, hyphen, inverted comma");
            return;
        }
        if(dob != null) {
           sqlDOB = Utils.getSqlDateFromLocalDate(dob);
            java.sql.Date currentSQLDate=new java.sql.Date(System.currentTimeMillis());
            if(sqlDOB.after(currentSQLDate) || sqlDOB.equals(currentSQLDate)) {
                Utils.showErrorDialog("Error Dialog", null, "Date of Birth can't be greater than or equal to current Date");
                return;
            }
        }
        if(phNo != null && !phNo.equals("") && !Utils.regexMatch(phNo,"^[0-9+-]+$", true )){
            Utils.showErrorDialog("Error Dialog", null, "Phone No can only contain digits, + and -");
            return;
        }
        if(email != null && !email.equals("") && !Utils.regexMatch(email,EMAIL_REGEX, true )){
            Utils.showErrorDialog("Error Dialog", null, "Email id has some invalid characters");
            return;
        }
        boolean isNewStudent = false;
        if(studentEntity == null) {
            studentEntity = new StudentEntity(); // student needs to be inserted - so create a new object
            isNewStudent = true;
        }
        studentEntity.setStudentFName(fName);
        studentEntity.setStudentMName(mName);
        studentEntity.setStudentLName(lName);

        studentEntity.setStudentDateOfBirth(sqlDOB);
        studentEntity.setStudentAddress(studentAddressTextField.getText());
        studentEntity.setStudentEmail(email);
        studentEntity.setStudentPhNo(phNo);
        if(isNewStudent){
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
