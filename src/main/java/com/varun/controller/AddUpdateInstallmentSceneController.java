//package com.varun.controller;
//
//import com.varun.App;
//import com.varun.ParameterStrings;
//import com.varun.Utils;
//import com.varun.db.managers.InstallmentManager;
//import com.varun.db.managers.RegistrationManager;
//import com.varun.db.models.InstallmentEntity;
//import com.varun.db.models.RegistrationEntity;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.TextField;
//import javafx.util.Pair;
//
//import java.io.IOException;
//import java.time.LocalDate;
//
//public class AddUpdateInstallmentSceneController {
//
//    public void setRegistrationEntity(RegistrationEntity registrationEntity) {
//        this.registrationEntity = registrationEntity;
//    }
//
//    private InstallmentEntity installmentEntity;
//    private RegistrationEntity registrationEntity;
//
//    @FXML private DatePicker installmentDueDatePicker;
//    @FXML private CheckBox alreadyPaidCheckBox;
//    @FXML private TextField installmentAmountTextField;
//
//
//    public void setInstallmentEntity(InstallmentEntity installmentEntity) {
//        this.installmentEntity = installmentEntity;
//    }
//
//    private void fillScene(){
//        if(installmentEntity != null){
//            //updating a new installment
//            //installmentEntity = InstallmentManager.getInstallmentById(installmentId);
//            installmentAmountTextField.setText(installmentEntity.getIntallmentAmount().toString());
//            alreadyPaidCheckBox.setSelected(installmentEntity.isInstalmentIsDone());
//            LocalDate localDate = Utils.getLocalDateFromString(installmentEntity.getInstallmentDueDate().toString());
//            installmentDueDatePicker.setValue(localDate);
//        }else{
//            //new installment
//            alreadyPaidCheckBox.setSelected(false);
//            installmentAmountTextField.setText("0.0");
//        }
//    }
///*
//RegistrationEntity : can never be null
//
// */
//    public static AddUpdateInstallmentSceneController display(String previousSceneName, Scene previousScene, RegistrationEntity registrationEntity, InstallmentEntity installmentEntity) throws IOException {
//        //this can only be called from addupdateregistration scene
//        if(previousSceneName.equals(ParameterStrings.addUpdateRegistrationString))
//            Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AddUpdateInstallmentScene" + ".fxml"));
//        Parent parent = fxmlLoader.load();
//        AddUpdateInstallmentSceneController addUpdateInstallmentSceneController =  fxmlLoader.getController();
//        addUpdateInstallmentSceneController.setInstallmentEntity(installmentEntity);
//        addUpdateInstallmentSceneController.setRegistrationEntity(registrationEntity);
//        addUpdateInstallmentSceneController.fillScene();
//        Scene newScene = new Scene(parent);
//        newScene.setRoot(parent);
//        App.setScene(newScene);
//        return addUpdateInstallmentSceneController;
//    }
//    @FXML
//    private void onSaveButtonClicked() throws IOException{
//        if(installmentEntity == null){
//            //inserting a new installment
//            installmentEntity = new InstallmentEntity();
//            installmentEntity.setInstallmentNo(1);
//            installmentEntity.setIntallmentAmount();
//        }else{
//            //updating an existing installment
//            int max = InstallmentManager.getMaxInstallmentNoForRegistration(registrationEntity.getRegistrationId());
//            if(max != -1)
//                installmentEntity.setInstallmentNo(max+1);
//
//
//        }
//    }
//    @FXML
//    private void onBackButtonClicked(){
//        Utils.backButtonFunctionality();
//    }
//    @FXML
//    private void onHomeButtonClicked() throws IOException {
//        Utils.homeButtonFunctionality();
//    }
//}
