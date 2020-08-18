package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.db.DBUtils;
import com.varun.db.managers.RegistrationManager;
import com.varun.db.models.RegistrationEntity;
import com.varun.fxmlmodels.RegistrationTableElem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationSceneController {

    @FXML private DatePicker sinceDatePicker, uptoDatePicker;
    @FXML private TableColumn<RegistrationTableElem, Integer> registrationIdCol;
    @FXML private TableColumn<RegistrationTableElem, String> studentNameCol;
    @FXML private TableColumn<RegistrationTableElem, String> courseNameCol;
    @FXML private TableColumn<RegistrationTableElem, Double> courseFeesCol;
    @FXML private TableColumn<RegistrationTableElem, Double> registrationDiscountCol;
    @FXML private TableColumn<RegistrationTableElem, Double> registrationAmountCol;
    @FXML private TableColumn<RegistrationTableElem, Double> registrationAmountPaidCol;
    @FXML private TableColumn<RegistrationTableElem, Date> registrationDateCol;
    @FXML TableView notificationTableView;
    List<RegistrationEntity> registrationEntityList;

    private void fillScene(){
        java.sql.Date since = new java.sql.Date(System.currentTimeMillis());
        java.sql.Date upto = new java.sql.Date(System.currentTimeMillis() + 1296000000); // upto 15 days
        sinceDatePicker.setValue(Utils.getLocalDateFromDate(since));
        uptoDatePicker.setValue(Utils.getLocalDateFromDate(upto));
        fillTableView(since, upto);
    }
    public static NotificationSceneController display(String previousSceneName, Scene previousScene) throws IOException {
        while(!Utils.sceneStack.empty() && !Utils.sceneStack.peek().getKey().equals(ParameterStrings.homeString))
            Utils.sceneStack.pop();
        if(Utils.sceneStack.empty() && previousSceneName.equals(ParameterStrings.homeString))
            Utils.sceneStack.push(new Pair(previousSceneName, previousScene));

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("NotificationScene" + ".fxml"));
        Parent parent = fxmlLoader.load();
        NotificationSceneController notificationSceneController =  fxmlLoader.getController();
        notificationSceneController.fillScene();
        Scene newScene = new Scene(parent);
        newScene.setRoot(parent);
        newScene.getStylesheets().add(notificationSceneController.getClass().getResource(ParameterStrings.cssResource).toExternalForm());

        App.setScene(newScene);
        return notificationSceneController;
    }

    @FXML private void initialize(){
        notificationTableView.setPlaceholder(new Label("No existing registrations"));
        notificationTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        registrationIdCol.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseFeesCol.setCellValueFactory(new PropertyValueFactory<>("courseFees"));
        registrationDiscountCol.setCellValueFactory(new PropertyValueFactory<>("registrationDiscount"));
        registrationAmountCol.setCellValueFactory(new PropertyValueFactory<>("registrationAmount"));
        registrationAmountPaidCol.setCellValueFactory(new PropertyValueFactory<>("registrationAmountPaid"));
        registrationDateCol.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        notificationTableView.setRowFactory(tv -> {
            TableRow<RegistrationTableElem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    RegistrationTableElem rowData = row.getItem();
                    System.out.println("Double click on Registration : "+rowData.getRegistrationId());
                    try {
                        RegistrationEntity registrationEntity = null;
                        for(RegistrationEntity reg : registrationEntityList){
                            if(reg.getRegistrationId() == rowData.getRegistrationId())
                                registrationEntity = reg;
                        }
                        //NOTE: at this point registration entity would be without the installations info - they would be needed to load lazily when required
                        registrationEntity = RegistrationManager.getRegistrationWithStudentInfoFromEntitiy(registrationEntity);
                        if(registrationEntity != null)
                            AddUpdateRegistrationSceneController.display(ParameterStrings.addUpdateStudentString, notificationTableView.getScene(), registrationEntity.getStudentByStudentId(), registrationEntity);
                        else
                            System.out.println("Registration Entity can't be null");
                    }catch(IOException ex){
                        System.out.println(ex);
                    }
                }
            });
            return row ;
        });

        sinceDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            java.sql.Date sinceSQLDate = Utils.getSqlDateFromLocalDate(newValue);
            java.sql.Date uptoSQLDate = Utils.getSqlDateFromLocalDate(uptoDatePicker.getValue());
            fillTableView(sinceSQLDate, uptoSQLDate);
        });

        uptoDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            java.sql.Date sinceSQLDate = Utils.getSqlDateFromLocalDate(sinceDatePicker.getValue());
            java.sql.Date uptoSQLDate = Utils.getSqlDateFromLocalDate(newValue);
            fillTableView(sinceSQLDate, uptoSQLDate);
        });
    }
    @FXML
    private void onBackButtonClicked(){
        Utils.backButtonFunctionality();
    }
    @FXML
    private void onHomeButtonClicked() throws IOException{
        Utils.homeButtonFunctionality();
    }
    //
    private void fillTableView(java.sql.Date since, java.sql.Date upto){
        if(upto.before(since)){
            Utils.showErrorDialog("Error Dialog", null, "Start Date should always be smaller than End Date");
            return;
        }
        registrationEntityList = RegistrationManager.getRegistrationsHavingInstallmentDueDateBetween(since, upto);
        ObservableList<RegistrationTableElem> registrationTableElems = FXCollections.observableArrayList();

        for(int i = 0; i < registrationEntityList.size(); i++){
            RegistrationEntity registrationEntity = registrationEntityList.get(i);
            registrationEntity = RegistrationManager.getRegistrationWithStudentInfoFromEntitiy(registrationEntity);
            String studentName = registrationEntity.getStudentByStudentId().getStudentFName() + " " + registrationEntity.getStudentByStudentId().getStudentMName() + " " + registrationEntity.getStudentByStudentId().getStudentLName();
            RegistrationTableElem registrationTableElem = DBUtils.getRegistrationTableElemFromRegistrationEntity(registrationEntity, studentName);
            registrationTableElems.add(registrationTableElem);
            registrationEntityList.set(i, registrationEntity);
        }
        notificationTableView.setItems(registrationTableElems);
    }

}
