module com.example.java_quanlyclbbida {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires java.sql;
    requires java.xml;

    opens com.example.java_quanlyclbbida to javafx.fxml;
    opens com.example.java_quanlyclbbida.model to org.hibernate.orm.core;
    exports com.example.java_quanlyclbbida;
    exports com.example.java_quanlyclbbida.controller;
    opens com.example.java_quanlyclbbida.controller to javafx.fxml;
}