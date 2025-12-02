package com.example.teacherApp;

import com.example.teacherApp.controllers.Login;
import com.example.teacherApp.dao.*;
import com.example.teacherApp.services.FilesManager;
import com.example.teacherApp.services.SettingsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.example.teacherApp.services.StudentsManager;
import com.example.teacherApp.services.TeachersManager;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        DataBase dataBase = new DataBase(); // This class is for connecting with SQLite.

        StudentsSQLite studentsSQLite = new StudentsSQLite(dataBase);// This is for SQLite.
        studentsSQLite.createStudentsTable();

        TeachersSQLite teachersSQLite = new TeachersSQLite(dataBase);// This is for SQLite.
        teachersSQLite.createTeachersTable();

        SavedFilesSQLite savedFilesSQLite = new SavedFilesSQLite(dataBase); // This is for SQLite.
        savedFilesSQLite.createSavedFilesTeachersTable();

        SettingsSQLite settingsSQLite = new SettingsSQLite(dataBase); // This is for SQLite.
        settingsSQLite.createSettingsTable();

        studentsSQLite.check();

        StudentsManager manager = new StudentsManager(studentsSQLite); // For students.
        TeachersManager teachersManager = new TeachersManager(teachersSQLite); // For teachers.
        FilesManager filesManager = new FilesManager(savedFilesSQLite); // For files.
        SettingsManager settingsManager = new SettingsManager(settingsSQLite); // For settings.

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/LoginScene.fxml"));
        Parent parent = fxmlLoader.load();
        Login login = fxmlLoader.getController();

        login.setFilesManager(filesManager);
        login.setStudentsManager(manager);
        login.setTeachersManager(teachersManager);
        login.setSettingsManager(settingsManager);

        Scene scene = new Scene(parent);
        String css = Objects.requireNonNull(this.getClass().getResource("/styles/LoginStyle.css")).toExternalForm();
        scene.getStylesheets().add(css);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/school.png")));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.setTitle("Students Management");
        stage.show();
    }
}