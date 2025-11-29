package com.example.teacherApp.controllers;

import com.example.teacherApp.models.Settings;
import com.example.teacherApp.services.FilesManager;
import com.example.teacherApp.services.SettingsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.example.teacherApp.models.Teacher;
import com.example.teacherApp.services.StudentsManager;
import com.example.teacherApp.services.TeachersManager;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login implements Initializable {

    private StudentsManager studentsManager;
    private TeachersManager teachersManager;
    private FilesManager filesManager;
    private SettingsManager settingsManager;

    @FXML private Button loginButton, forgetPasswordButton, newAccountButton;
    @FXML private Label welcomeLabel;
    @FXML private TextField userField;
    @FXML private PasswordField passwordField;
    @FXML private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.setBackground(new Background(new BackgroundFill(Color.LIGHTSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @FXML // This is for logging into the user's account.
    private void loginAccount(ActionEvent event) throws IOException {
        String name = userField.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        boolean userIsFound = teachersManager.selectTeacher(name, passwordField.getText());

        if (name.isEmpty() && passwordField.getText().isEmpty()) {
            alert.setTitle("A problem");
            alert.setHeaderText("Fields are empty");
            alert.setContentText("The password or the username is empty");
            alert.showAndWait();
        }
        else if (!userIsFound) {
            alert.setTitle("Account");
            alert.setHeaderText("Couldn't log into your account");
            alert.setContentText("The password or the username is incorrect.");
            alert.showAndWait();
        }
        else {
            mainPage(event,teachersManager.getTeacher(),settingsManager.getUserSettings(teachersManager.getUserID()));
        }
    }

    @FXML // This is for forgetting the password.
    private  void forgetPassword(ActionEvent event) throws IOException {
        String page = "ForgetPasswordScene";
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/scenes/"+page+".fxml")));
        Parent parent = loader.load();
        ForgetPasswordController forgetPasswordController = loader.getController();

        forgetPasswordController.setStudentsManager(studentsManager);
        forgetPasswordController.setTeachersManager(teachersManager);
        forgetPasswordController.setFilesManager(filesManager);
        forgetPasswordController.setSettingsManager(settingsManager);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        String css = Objects.requireNonNull(this.getClass().getResource("/styles/ForgetPasswordStyle.css")).toExternalForm();
        scene.getStylesheets().add(css);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/school.png")));
        stage.setTitle("Forget password");
        stage.setScene(scene);
        stage.getIcons().add(image);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML // This is for creating a new account.
    private void newAccount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/RegisterScene.fxml"));
        Parent parent = loader.load();
        Register register = loader.getController();

        register.setFilesManager(filesManager);
        register.setStudentsManager(studentsManager);
        register.setTeachersManager(teachersManager);
        register.setSettingsManager(settingsManager);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        String css = Objects.requireNonNull(this.getClass().getResource("/styles/RegisterStyle.css")).toExternalForm();
        scene.getStylesheets().add(css);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/school.png")));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.getIcons().add(image);
        stage.setTitle("Creating a new account");
        stage.show();
    }

    private void mainPage(ActionEvent event, Teacher teacher, Settings settings) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Scenes/HomeScene.fxml")));
        Parent parent = loader.load();
        Home home = loader.getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        home.setFilesManager(filesManager);
        home.setStudentsManager(studentsManager);
        home.setTeachersManager(teachersManager);
        home.setSettingsManager(settingsManager);
        home.setUserIsFound(true);
        home.setTeacher(teacher);
        home.setSettings(settings);

        Scene scene = new Scene(parent);
        String css = Objects.requireNonNull(this.getClass().getResource("/styles/HomeStyle.css")).toExternalForm();
        scene.getStylesheets().add(css);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/school.png")));
        stage.setScene(scene);
        stage.getIcons().add(image);
        stage.centerOnScreen();
        stage.setTitle("Home");
        stage.show();
    }

    public void setFilesManager(FilesManager filesManager) {
        this.filesManager = filesManager;
    }
    public void setStudentsManager(StudentsManager studentsManager) {
        this.studentsManager = studentsManager;
    }
    public void setTeachersManager(TeachersManager teachersManager) {
        this.teachersManager = teachersManager;
    }
    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }
}
