package com.varun.controller;

import com.varun.App;
import com.varun.Utils;
import com.varun.db.models.RegistrationEntity;
import com.varun.fxmlmodels.CourseTableElem;
import com.varun.fxmlmodels.InstallmentTableElem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Date;

public class InstallmentDetailsController {
    @FXML TableView installmentTableView;
    @FXML Button addInstallmentButton;
    @FXML private TableColumn<InstallmentTableElem, Integer> installmentIdCol;
    @FXML private TableColumn<InstallmentTableElem, Double> amountCol;
    @FXML private TableColumn<InstallmentTableElem, Boolean> alreadPaidCol;
    @FXML private TableColumn<InstallmentTableElem, Date> dueDateCol;
    private ObservableList<InstallmentTableElem> installmentTableElems;

    public void setInstallmentTableContents(ObservableList<InstallmentTableElem> installmentTableElems){
        this.installmentTableElems = installmentTableElems;
        installmentTableView.getItems().setAll(installmentTableElems);
    }
    @FXML
    private void initialize() {
        installmentTableView.setPlaceholder(new Label("No installments added"));
        installmentIdCol.setCellValueFactory(new PropertyValueFactory<>("installmentId"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("installmentAmount"));
        alreadPaidCol.setCellValueFactory(new PropertyValueFactory<>("installmentPaid"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("installmentDueDate"));
        //installmentTableView.getItems().setAll(Utils.getTestInstallmentTableElem());

        //TODO: this list needs to come from RegistrationDetailsController
    }
    public static Parent getParentForInstallment(ObservableList<InstallmentTableElem> installmentTableElems) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("InstallmentDetails" + ".fxml"));
        Parent parent = fxmlLoader.load();
        ((InstallmentDetailsController)(fxmlLoader.getController())).setInstallmentTableContents(installmentTableElems);
        return parent;
    }

}
