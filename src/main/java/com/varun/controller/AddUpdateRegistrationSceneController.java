package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.db.managers.CourseManager;
import com.varun.db.managers.RegistrationManager;
import com.varun.db.managers.StudentManager;
import com.varun.db.models.CourseEntity;
import com.varun.db.models.InstallmentEntity;
import com.varun.db.models.RegistrationEntity;
import com.varun.db.models.StudentEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class AddUpdateRegistrationSceneController {
    @FXML private Label registrationIdLabel, studentNameLabel, courseFeesLabel, amountPaidLabel, finalFeesLabel;
    @FXML private ChoiceBox<String> courseNameChoiceBox;
    @FXML private TextField discountTextField;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox mainVBox;
    @FXML private Button addInstallmentButton;
    @FXML private TableView installmentTableView;
    private RegistrationEntity registrationEntity;
    private int registrationId;

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    private StudentEntity studentEntity = null;
    private List<CourseEntity> courseEntities;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    private int studentId;

    @FXML
    private void initialize(){
        courseEntities = CourseManager.getCourseDBList();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for(CourseEntity courseEntity : courseEntities)
            observableList.add(courseEntity.getCourseName());
        courseNameChoiceBox.setItems(observableList);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        // add a listener
        courseNameChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                CourseEntity courseEntity = null;
                Iterator<CourseEntity> iterator = courseEntities.iterator();
                while(iterator.hasNext()){
                    courseEntity = iterator.next();
                    if(courseEntity.getCourseName().equals(courseNameChoiceBox.getValue()))
                        break;
                }
//                if(courseEntity.getCourseId() == registrationEntity.getCourseId()){
                    courseFeesLabel.setText(courseEntity.getCourseFees().toString());
                    if(!discountTextField.getText().isEmpty()){
                        Double discount = Double.parseDouble(discountTextField.getText());
                        double finalFees = courseEntity.getCourseFees().doubleValue() * (100.0 - discount) / 100.0;
                        finalFeesLabel.setText(Double.toString(finalFees));
                    }
//                }
            }
        });
        discountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            onDiscountTextChanged(oldValue, newValue);
        });
    }
    public void fillScene(){
        if(studentEntity == null){
            studentEntity = StudentManager.getStudentByIdWithoutRegistrations(studentId);
        }
        if(registrationId != -1) {
            //updating an existing registration
            registrationEntity = RegistrationManager.getRegistrationById(registrationId);
            registrationIdLabel.setText(Integer.toString(registrationEntity.getRegistrationId()));
            Iterator<CourseEntity> iterator = courseEntities.iterator();
            CourseEntity courseEntity = null;
            while(iterator.hasNext()){
                courseEntity = iterator.next();
                if(courseEntity.getCourseId() == registrationEntity.getCourseId())
                    break;
            }
            if(courseEntity.getCourseId() == registrationEntity.getCourseId()){
                courseNameChoiceBox.setValue(courseEntity.getCourseName());
                courseFeesLabel.setText(courseEntity.getCourseFees().toString());
            }
            double finalFees = courseEntity.getCourseFees().doubleValue() * (100.0 - registrationEntity.getDiscount().doubleValue()) / 100.0;
            finalFeesLabel.setText(Double.toString(finalFees));

            if(registrationEntity.getInstallmentsByRegistrationId() == null){
                List<InstallmentEntity> installmentEntities = RegistrationManager.getInstallmentsForRegistration(registrationEntity);
                Double amountPaid = 0.0;
                for(InstallmentEntity installmentEntity : installmentEntities){
                    if(installmentEntity.isInstalmentIsDone())
                        amountPaid += installmentEntity.getIntallmentAmount().doubleValue();
                }
                amountPaidLabel.setText(Double.toString(amountPaid));
            }
            discountTextField.setText(registrationEntity.getDiscount().toString());
        }else{
            //adding a new registration
            registrationIdLabel.setText("");
            amountPaidLabel.setText("0.0");
            CourseEntity courseEntity = courseEntities.iterator().hasNext() ? courseEntities.iterator().next() : null;
            if(courseEntity != null) {
                courseNameChoiceBox.setValue(courseEntity.getCourseName());
                courseFeesLabel.setText(courseEntity.getCourseFees().toString());
                finalFeesLabel.setText(courseEntity.getCourseFees().toString());
            }
            discountTextField.setText("0.0");
            mainVBox.getChildren().remove(addInstallmentButton);
            mainVBox.getChildren().remove(installmentTableView);

        }
        studentNameLabel.setText(studentEntity.getStudentFName() + " " + studentEntity.getStudentMName() + " " + studentEntity.getStudentLName());
    }
    public static AddUpdateRegistrationSceneController display(String previousSceneName, Scene previousScene, int studentId, int registrationId) throws IOException {
        if(previousSceneName.equals(ParameterStrings.addUpdateStudentString))
            Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
        else if(previousSceneName.equals(ParameterStrings.addUpdateInstallmentString))
            while (!Utils.sceneStack.peek().getKey().equals(ParameterStrings.addUpdateStudentString))
                Utils.sceneStack.pop();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AddUpdateRegistrationScene" + ".fxml"));
        Parent parent = fxmlLoader.load();
        AddUpdateRegistrationSceneController addUpdateRegistrationSceneController =  fxmlLoader.getController();
        addUpdateRegistrationSceneController.setRegistrationId(registrationId);
        addUpdateRegistrationSceneController.setStudentId(studentId);
        addUpdateRegistrationSceneController.fillScene();
        Scene newScene = new Scene(parent);
        newScene.setRoot(parent);
        App.setScene(newScene);
        return addUpdateRegistrationSceneController;
    }
    @FXML
    private void onBackButtonClicked(){
        Utils.backButtonFunctionality();
    }
    @FXML
    private void onHomeButtonClicked() throws IOException {
        Utils.homeButtonFunctionality();
    }
    @FXML
    private void onSubmitButtonClicked() throws IOException {
        Double discount = Double.parseDouble(discountTextField.getText());
        String courseName = courseNameChoiceBox.getValue();
        CourseEntity courseEntity = null;
        for(int i = 0; i < courseEntities.size(); i++){
            if(courseEntities.get(i).getCourseName().equals(courseName)){
                courseEntity = courseEntities.get(i);
                break;
            }
        }
        //RegistrationEntity registrationEntity = new RegistrationEntity();
        if(registrationId == -1)
            registrationEntity = new RegistrationEntity();
        registrationEntity.setCourseId(courseEntity.getCourseId());
        registrationEntity.setStudentId(studentId);
        registrationEntity.setDiscount(new BigDecimal(discount));
        registrationEntity.setCourseByCourseId(courseEntity);
        registrationEntity.setStudentByStudentId(studentEntity);
        System.out.println("courseId : " +  registrationEntity.getCourseId());

        if(registrationId != -1){
            //update
            registrationEntity.setRegistrationDate(null);
            System.out.println("update registration");
            RegistrationManager.updateRegistration(registrationEntity);
        }else{
            //insert
            System.out.println("insert registration");
            RegistrationManager.addRegistration(registrationEntity);
        }
        AddUpdateStudentSceneController.display(ParameterStrings.addUpdateRegistrationString, addInstallmentButton.getScene(), studentId);
    }
    @FXML
    private void onAddInstallmentButtonClicked() throws IOException {
    }
    private void onDiscountTextChanged(String oldValue, String newValue){
        Double discount = 0.0;
        try {
            discount = Double.parseDouble(newValue);
        }catch(NumberFormatException ex){
            System.out.println(ex.getMessage());
            discountTextField.setText("0.0");
        }
        double finalFees = Double.parseDouble(courseFeesLabel.getText()) * (100.0 - discount) / 100.0;
        finalFeesLabel.setText(Double.toString(finalFees));
    }

}
