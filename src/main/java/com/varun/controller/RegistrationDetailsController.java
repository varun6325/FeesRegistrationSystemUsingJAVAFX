package com.varun.controller;

import com.varun.App;
import com.varun.Utils;
import com.varun.db.models.RegistrationEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class RegistrationDetailsController {

    private InstallmentDetailsController installmentDetailsController;
    private RegistrationEntity registrationEntity;
    @FXML
    private Label registrationNameLabel;
    @FXML private GridPane registrationGridPane;
    @FXML private TitledPane installmentsTitledPane;

    @FXML
    private void initialize() throws IOException{
        registrationNameLabel.setText("Registration");
        Label courseNameLabel = new Label("Course Name");
        TextField courseNameTextField = new TextField("Test Course");
        Label courseFeesLabel = new Label("Course Fees");
        TextField courseFeesTextField = new TextField("2300.00");
        Label discountLabel = new Label("Discount");
        TextField discountTextField = new TextField("10");
        Label feesToBePaidLabel = new Label("Fees To Be Paid");
        TextField feesToBePaidTextField = new TextField("2000.00");
        Label feesPaidLabel = new Label("Amount Already Paid");
        TextField feesPaidTextField = new TextField("1500.00");
        Label registrationDateLabel = new Label("Registration Date");
        TextField registrationDateTextField = new TextField("Test Date");
        registrationGridPane.add(courseNameLabel, 0, 0, 1, 1);
        registrationGridPane.add(courseNameTextField, 1, 0, 1, 1);
        registrationGridPane.add(courseFeesLabel, 2, 0, 1, 1);
        registrationGridPane.add(courseFeesTextField, 3, 0, 1, 1);
        registrationGridPane.add(discountLabel, 0, 1, 1, 1);
        registrationGridPane.add(discountTextField, 1, 1, 1, 1);
        registrationGridPane.add(feesToBePaidLabel, 2, 1, 1, 1);
        registrationGridPane.add(feesToBePaidTextField, 3, 1, 1, 1);
        registrationGridPane.add(feesPaidLabel, 0, 2, 1, 1);
        registrationGridPane.add(feesPaidTextField, 1, 2, 1, 1);
        registrationGridPane.add(registrationDateLabel, 2, 2, 1, 1);
        registrationGridPane.add(registrationDateTextField, 3, 2, 1, 1);
        //TODO: the above needs to come from AddUpdateStudentController
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("InstallmentDetails" + ".fxml"));
        Parent parent = fxmlLoader.load();
        installmentDetailsController = (InstallmentDetailsController)(fxmlLoader.getController());
        installmentDetailsController.setInstallmentTableContents(Utils.getTestInstallmentTableElem());
        installmentsTitledPane.setContent(parent);
//        installmentsTitledPane.setContent(InstallmentDetailsController.getParentForInstallment(Utils.getTestInstallmentTableElem()));
    }

    public void setRegistrationEntity(RegistrationEntity registrationEntity) { // Setting the client-object in ClientViewController
        this.registrationEntity = registrationEntity;
    }
}
