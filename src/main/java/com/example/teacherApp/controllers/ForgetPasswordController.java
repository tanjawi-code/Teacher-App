package com.example.teacherApp.controllers;

import com.example.teacherApp.services.FilesManager;
import com.example.teacherApp.services.SettingsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.example.teacherApp.models.Teacher;
import com.example.teacherApp.services.StudentsManager;
import com.example.teacherApp.services.TeachersManager;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ForgetPasswordController {

    private StudentsManager studentsManager;
    private TeachersManager teachersManager;
    private FilesManager filesManager;
    private SettingsManager settingsManager;

    @FXML private TextField userEmailField;

    @FXML // This is for sending the email address.
    private void submit() {
        if (userEmailField.getText().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION,"Email is empty", ButtonType.OK).showAndWait();
        }
        else {
            boolean userIsFound = teachersManager.checkUserEmail(userEmailField.getText());
            if (userIsFound) {
                new Alert(Alert.AlertType.INFORMATION,"Not available yet",ButtonType.OK).showAndWait();
                //sendToEmail(userEmailField.getText(), teachersManager.getTeacher());
            }
            else {
                new Alert(Alert.AlertType.INFORMATION, "We could not find this email address on our data.",ButtonType.OK).showAndWait();
            }
        }
    }

    private void sendToEmail(String text, Teacher teacher) {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
    }

    @FXML // This is for going back to login page.
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/scenes/LoginScene.fxml")));
        Parent parent = loader.load();
        Login login = loader.getController();

        login.setFilesManager(filesManager);
        login.setStudentsManager(studentsManager);
        login.setTeachersManager(teachersManager);
        login.setSettingsManager(settingsManager);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        String css = Objects.requireNonNull(this.getClass().getResource("/styles/LoginStyle.css")).toExternalForm();
        scene.getStylesheets().add(css);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/school.png")));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.getIcons().add(image);
        stage.setTitle("Students Management");
        stage.show();
    }

    public void setStudentsManager(StudentsManager studentsManager) {
        this.studentsManager = studentsManager;
    }
    public void setTeachersManager(TeachersManager teachersManager) {
        this.teachersManager = teachersManager;
    }
    public void setFilesManager(FilesManager filesManager) {
        this.filesManager = filesManager;
    }
    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }
}
