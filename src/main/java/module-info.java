module TeacherApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires javafx.graphics;
    requires java.desktop;
    requires org.xerial.sqlitejdbc;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires java.sql;

    opens com.example.teacherApp.controllers to javafx.fxml;
    opens com.example.teacherApp.models to javafx.base, com.fasterxml.jackson.databind;
    opens com.example.teacherApp.Enums to javafx.fxml;
    opens com.example.teacherApp.interfaces to javafx.fxml;
    opens com.example.teacherApp.services to javafx.fxml;
    opens com.example.teacherApp.dao to javafx.base;

    exports com.example.teacherApp.models;
    exports com.example.teacherApp.Enums;
    exports com.example.teacherApp;
}