module com.varun {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.varun to javafx.fxml;
    opens com.varun.controller to javafx.fxml;
    opens com.varun.fxmlmodels to javafx.base;
    exports com.varun;

}