module com.varun {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires java.naming;

    opens com.varun.controller to javafx.fxml;
    opens com.varun.fxmlmodels to javafx.base;

    exports com.varun;
    exports com.varun.db.models to org.hibernate.orm.core;


}