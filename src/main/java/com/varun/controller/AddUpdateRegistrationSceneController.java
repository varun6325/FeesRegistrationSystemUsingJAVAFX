package com.varun.controller;

import com.varun.App;
import com.varun.ParameterStrings;
import com.varun.Utils;
import com.varun.db.DBUtils;
import com.varun.db.managers.CourseManager;
import com.varun.db.managers.InstallmentManager;
import com.varun.db.managers.RegistrationManager;
import com.varun.db.managers.StudentManager;
import com.varun.db.models.CourseEntity;
import com.varun.db.models.InstallmentEntity;
import com.varun.db.models.RegistrationEntity;
import com.varun.db.models.StudentEntity;
import com.varun.fxmlmodels.InstallmentTableElem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class AddUpdateRegistrationSceneController {

    @FXML private Label registrationIdLabel, studentNameLabel, courseFeesLabel, amountPaidLabel, finalFeesLabel;
    @FXML private ChoiceBox<String> courseNameChoiceBox;
    @FXML private TextField discountTextField;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox mainVBox;
    @FXML private Button addInstallmentButton;
    @FXML private TableView installmentTableView;
    @FXML private TableColumn<InstallmentTableElem, Integer> installmentNoCol;
    @FXML private TableColumn<InstallmentTableElem, Double> amountCol;
    @FXML private TableColumn<InstallmentTableElem, Boolean> alreadPaidCol;
    @FXML private TableColumn<InstallmentTableElem, Date> dueDateCol;
    private StudentEntity studentEntity = null;
    private List<CourseEntity> courseEntities;
    private RegistrationEntity registrationEntity;
    private List<InstallmentEntity> installmentEntities;
    int indexForNewInstallments = 0; // this is used for setting the installment id as -ve so that it can be used to identify new installment vs modified installments
    public void setRegistrationEntity(RegistrationEntity registrationEntity) {
        this.registrationEntity = registrationEntity;
    }

    public void setStudentEntity(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
    }

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
        installmentNoCol.setCellValueFactory(new PropertyValueFactory<>("installmentNo"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("installmentAmount"));
        alreadPaidCol.setCellValueFactory(new PropertyValueFactory<>("isInstallmentPaid"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("installmentDueDate"));
        installmentTableView.setRowFactory(tv -> {
            TableRow<InstallmentTableElem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    InstallmentTableElem rowData = row.getItem();
                    System.out.println("Double click on Installment : "+rowData.getInstallmentNo());
                    try {
                        int instId = rowData.getInstallmentId();
                        InstallmentEntity ent = null;
                        for(InstallmentEntity installmentEntity : installmentEntities){
                            if(installmentEntity.getInstallmentId() == instId) {
                                ent = installmentEntity;
                                break;
                            }
                        }
                        Dialog dialog = showAddUpdateInstallmentDialog(ent);
                        Optional<InstallmentEntity> result = dialog.showAndWait();

                        if (result.isPresent()) {
                            InstallmentEntity installmentEntity = result.get();
                            installmentEntity.setInstallmentId(ent.getInstallmentId());
                            installmentEntity.setInstallmentNo(ent.getInstallmentNo());
                            installmentEntities.remove(ent);
                            installmentEntities.add(installmentEntity);
                            fillInstallmentTableView();
                        }else{
                            System.out.println("result is not present in dialog");
                        }
                    }catch(Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }
            });
            return row ;
        });
    }
    public void fillScene(){
        if(registrationEntity != null) {
            //updating an existing registration
 //           registrationEntity = RegistrationManager.getRegistrationById(registrationId);
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

            installmentEntities = RegistrationManager.getInstallmentsForRegistration(registrationEntity);
            if(installmentEntities != null) {
                Double amountPaid = 0.0;
                for (InstallmentEntity installmentEntity : installmentEntities) {
                    if (installmentEntity.isInstalmentIsDone())
                        amountPaid += installmentEntity.getIntallmentAmount().doubleValue();
                }
                amountPaidLabel.setText(Double.toString(amountPaid));
                fillInstallmentTableView();
            }else{
                installmentEntities = new ArrayList();
            }
            courseNameChoiceBox.setDisable(true);
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
    public static AddUpdateRegistrationSceneController display(String previousSceneName, Scene previousScene, StudentEntity studentEntity, RegistrationEntity registrationEntity) throws IOException {
        if(previousSceneName.equals(ParameterStrings.addUpdateStudentString))
            Utils.sceneStack.push(new Pair(previousSceneName, previousScene));
        else if(previousSceneName.equals(ParameterStrings.addUpdateInstallmentString))
            while (!Utils.sceneStack.peek().getKey().equals(ParameterStrings.addUpdateStudentString))
                Utils.sceneStack.pop();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AddUpdateRegistrationScene" + ".fxml"));
        Parent parent = fxmlLoader.load();
        AddUpdateRegistrationSceneController addUpdateRegistrationSceneController =  fxmlLoader.getController();
        addUpdateRegistrationSceneController.setStudentEntity(studentEntity);
        addUpdateRegistrationSceneController.setRegistrationEntity(registrationEntity);
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
        boolean isNewRegistration = false;
        if(registrationEntity == null) {
            registrationEntity = new RegistrationEntity();
            isNewRegistration = true;
        }
        registrationEntity.setCourseId(courseEntity.getCourseId());
        registrationEntity.setStudentId(studentEntity.getStudentId());
        registrationEntity.setDiscount(new BigDecimal(discount));
        registrationEntity.setCourseByCourseId(courseEntity);
        registrationEntity.setStudentByStudentId(studentEntity);
        System.out.println("courseId : " +  registrationEntity.getCourseId());

        if(!isNewRegistration){
            //updation of an existing registration
            //studentEntity.getRegistrationsByStudentId().add(registrationEntity);

            //registrationEntity.setRegistrationDate(null);
            registrationEntity.setInstallmentsByRegistrationId(installmentEntities);
            for(InstallmentEntity installmentEntity : installmentEntities){
                if(installmentEntity.getInstallmentId() < 0){
                    //installment no is not implemented as for now.
                    installmentEntity.setInstallmentId(0);
                }
                installmentEntity.setRegistrationByRegistrationId(registrationEntity);
            }

            registrationEntity.setInstallmentsByRegistrationId(installmentEntities);
            System.out.println("update registration");
            //RegistrationManager.updateRegistration(registrationEntity);
            studentEntity = StudentManager.updateStudent(studentEntity);
        }else{
            //insertion of new registration
            System.out.println("insert registration");
            studentEntity.getRegistrationsByStudentId().add(registrationEntity);
            //RegistrationManager.addRegistration(registrationEntity);
            studentEntity = StudentManager.updateStudent(studentEntity);
        }
        AddUpdateStudentSceneController.display(ParameterStrings.addUpdateRegistrationString, addInstallmentButton.getScene(), studentEntity);
    }
    @FXML
    private void onAddInstallmentButtonClicked() throws IOException {
        Dialog dialog = showAddUpdateInstallmentDialog(null);
        Optional<InstallmentEntity> result = dialog.showAndWait();

        if (result.isPresent()) {
            InstallmentEntity installmentEntity = result.get();
            //installmentEntity.setInstallmentNo(-1);
            indexForNewInstallments--;
            installmentEntity.setInstallmentNo(indexForNewInstallments);
            installmentEntities.add(installmentEntity);
            fillInstallmentTableView();
        }
    }
    private void onDiscountTextChanged(String oldValue, String newValue){
        Double discount = 0.0;
        try {
            discount = Double.parseDouble(newValue);
        }catch(NumberFormatException ex){
            System.out.println(ex.getMessage());
            discountTextField.setText("0.0");
        }
        if(discount > 100.0 || discount < 0.0){
            Utils.showErrorDialog("Error Dialog", "", "discount should be between 0 and 100");
            discountTextField.setText("0.0");
        }
        double finalFees = Double.parseDouble(courseFeesLabel.getText()) * (100.0 - discount) / 100.0;
        finalFeesLabel.setText(Double.toString(finalFees));
    }
    private Dialog showAddUpdateInstallmentDialog(InstallmentEntity installmentEntity){
        Dialog<InstallmentEntity> dialog = new Dialog<>();
        dialog.setTitle("Add/Update Installment");
        dialog.setHeaderText(null);
        dialog.setResizable(true);

        Label amtLabel = new Label("Amount: ");
        Label isPaidLabel = new Label("Is Paid: ");
        Label dueDateLabel = new Label("Due Date: ");
        Label paidDateLabel = new Label("Paid Date: ");
        TextField amtTF = new TextField();
        CheckBox isPaidCB = new CheckBox();
        DatePicker dueDatePicker = new DatePicker();
        DatePicker paidDatePicker = new DatePicker();

        ColumnConstraints column1Constraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        //column1Constraints.setHgrow(Priority.ALWAYS);
        column1Constraints.setHalignment(HPos.CENTER);
        ColumnConstraints column2Constraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        column2Constraints.setHgrow(Priority.ALWAYS);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.getColumnConstraints().addAll(column1Constraints,column2Constraints);
        grid.add(amtLabel, 0, 0);
        grid.add(amtTF, 1, 0);
        grid.add(isPaidLabel, 0, 1);
        grid.add(isPaidCB, 1, 1);
        grid.add(dueDateLabel, 0, 2);
        grid.add(dueDatePicker, 1, 2);
        grid.add(paidDateLabel, 0, 3);
        grid.add(paidDatePicker, 1, 3);
        dialog.getDialogPane().setContent(grid);
        if(installmentEntity != null){
            amtTF.setText(installmentEntity.getIntallmentAmount().toString());
            isPaidCB.setSelected(installmentEntity.isInstalmentIsDone());
            if(installmentEntity.getInstallmentDueDate() != null)
                dueDatePicker.setValue(Utils.getLocalDateFromDate(installmentEntity.getInstallmentDueDate()));
            if(installmentEntity.getInstallmentPaidDate() != null)
                paidDatePicker.setValue(Utils.getLocalDateFromDate(installmentEntity.getInstallmentPaidDate()));
        }else{
            amtTF.setText("0.0");
        }

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(addButtonType);

        Button addButton = (Button)dialog.getDialogPane().lookupButton(addButtonType);
        // the following is added for input validation
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            try{
                Double.parseDouble(amtTF.getText());
            }catch(NumberFormatException ex){
                System.out.println(ex);
                Utils.showErrorDialog("Dialog Error", "", "installment amount is not valid");
                event.consume();
                return;
            }
            Double amount = Double.parseDouble(amtTF.getText());
            if(amount <= 0.0){
                Utils.showErrorDialog("Dialog Error", "", "installment amount cannot be less that 0");
                event.consume();
                return;
            }
            Double totalInstallmentsAmount = 0.0;
            for (InstallmentEntity entity : installmentEntities) {
                totalInstallmentsAmount += entity.getIntallmentAmount().doubleValue();
            }
            Double amountPending = Double.parseDouble(finalFeesLabel.getText()) - totalInstallmentsAmount;
            if(amountPending < 0.0){
                Utils.showErrorDialog("Dialog Error", "", "Installment amount is greater than the pending amount");
                event.consume();
                return;
            }
            if(dueDatePicker.getValue() != null) {
                java.sql.Date sqlDate = Utils.getSqlDateFromLocalDate(dueDatePicker.getValue());
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);
                java.sql.Date currentSQLDate=new java.sql.Date(today.getTimeInMillis());
                if(sqlDate.before(currentSQLDate)) {
                    Utils.showErrorDialog("Error Dialog", null, "Due date cannot be before than the current date");
                    event.consume();
                    return;
                }
            }else{
                Utils.showErrorDialog("Error Dialog", null, "Due date should always be entered");
                event.consume();
                return;
            }
            if(isPaidCB.isSelected() && paidDatePicker.getValue() == null){
                Utils.showErrorDialog("Error Dialog", null, "Paid Date can't be empty since the already paid check box is selected");
                event.consume();
                return;
            }
            if(paidDatePicker.getValue() != null && !isPaidCB.isSelected()){
                Utils.showErrorDialog("Error Dialog", null, "Is Paid check box should be selected if you are entering paid date.");
                event.consume();
                return;
            }
        });
        dialog.setResultConverter(new Callback<ButtonType, InstallmentEntity>() {
            @Override
            public InstallmentEntity call(ButtonType b) {

                if (b == addButtonType) {
                    //return new InstallmentDialogInfo(Double.parseDouble(amtTF.getText()), isPaidCB.isSelected(), date);

                    InstallmentEntity ret = new InstallmentEntity();
                    ret.setIntallmentAmount(new BigDecimal(Double.parseDouble(amtTF.getText())));
                    java.sql.Date sqlDate;
                    ret.setInstalmentIsDone(isPaidCB.isSelected());
                    if(dueDatePicker.getValue() != null) {
                        sqlDate = Utils.getSqlDateFromLocalDate(dueDatePicker.getValue());
                        ret.setInstallmentDueDate(sqlDate);
                    }
                    if(paidDatePicker.getValue() != null) {
                        sqlDate = Utils.getSqlDateFromLocalDate(paidDatePicker.getValue());
                        ret.setInstallmentPaidDate(sqlDate);
                    }
                    return ret;
                }
                return null;
            }
        });
        return dialog;
    }

    private void fillInstallmentTableView(){
        //List<InstallmentEntity> installmentEntities = (List)registrationEntity.getInstallmentsByRegistrationId();
        ObservableList<InstallmentTableElem> installmentTableElems = FXCollections.observableArrayList();
        int i = 1;
        Double amountPaid = 0.0;
        for(InstallmentEntity installmentEntity : installmentEntities){
           InstallmentTableElem installmentTableElem = DBUtils.getInstallmentTableElemFromInstallmentEntity(installmentEntity);
           installmentTableElem.setInstallmentNo(i++);
           installmentTableElems.add(installmentTableElem);
           if(installmentEntity.isInstalmentIsDone())
               amountPaid += installmentEntity.getIntallmentAmount().doubleValue();
        }
        installmentTableView.setItems(installmentTableElems);
        amountPaidLabel.setText(Double.toString(amountPaid));
    }

}
