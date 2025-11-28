package com.example.teacherApp.controllers;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.Enums.SchoolsLevel;
import com.example.teacherApp.Enums.Subjects;
import com.example.teacherApp.dao.SettingsSQLite;
import com.example.teacherApp.models.Settings;
import com.example.teacherApp.models.Teacher;
import com.example.teacherApp.services.FilesManager;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.example.teacherApp.services.StudentsManager;
import com.example.teacherApp.services.TeachersManager;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Register implements Initializable {

    private FilesManager filesManager;
    private StudentsManager studentsManager;
    private TeachersManager teachersManager;
    private SettingsSQLite settingsSQLite;

    @FXML private ChoiceBox<Gender> genderChoice;
    @FXML private ChoiceBox<City> cityChoice;
    @FXML private ChoiceBox<Subjects> subjectChoice;
    @FXML private ChoiceBox<SchoolsLevel> schoolLevelChoice;
    @FXML private TextField nameField, ageField, emailField, mobilePhoneField;
    @FXML private AnchorPane anchorPane;
    @FXML private GridPane gridPane;
    @FXML private Button clearButton, backButton, hidePasswordButton, showPasswordButton, createAccountButton;
    @FXML private PasswordField passwordField, confirmPasswordField;

    private TextField showPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderChoice.setItems(FXCollections.observableArrayList(Gender.values()));
        cityChoice.setItems(FXCollections.observableArrayList(City.values()));
        schoolLevelChoice.setItems(FXCollections.observableArrayList(SchoolsLevel.values()));
        anchorPane.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,CornerRadii.EMPTY,Insets.EMPTY)));

        schoolLevelChoice.getSelectionModel().selectedItemProperty().addListener
                ((_, _, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case HIGH_SCHOOL -> setSubjectsBasedOnSchoolLevel(SchoolsLevel.HIGH_SCHOOL);
                    case MIDDLE_SCHOOL -> setSubjectsBasedOnSchoolLevel(SchoolsLevel.MIDDLE_SCHOOL);
                    case ELEMENTARY_SCHOOL -> setSubjectsBasedOnSchoolLevel(SchoolsLevel.ELEMENTARY_SCHOOL);
                }
            }
            else {
                subjectChoice.getItems().clear();
            }
        });
    }

    // This is for selecting the subject based on the school level.
    private void setSubjectsBasedOnSchoolLevel(SchoolsLevel schoolLevel) {
        Subjects[] elementary = {Subjects.ARABIC, Subjects.AMAZIGH, Subjects.ISLAMIC_EDUCATION,
                                 Subjects.SCIENCE_AND_TECHNOLOGY, Subjects.MATHEMATICS, Subjects.FRENCH,
                                 Subjects.ART_EDUCATION, Subjects.ENGLISH, Subjects.PHYSICAL_EDUCATION};

        Subjects[] middle = {Subjects.ARABIC, Subjects.FRENCH, Subjects.ENGLISH, Subjects.MATHEMATICS,
                Subjects.LIFE_AND_EARTH_NATURAL_SCIENCES, Subjects.PHYSICS_AND_CHEMISTRY,
                Subjects.ISLAMIC_EDUCATION, Subjects.SOCIAL_STUDIES, Subjects.ART_EDUCATION,
                Subjects.COMPUTER_SCIENCE, Subjects.PHYSICAL_EDUCATION};

        Subjects[] high = {Subjects.ARABIC, Subjects.ENGLISH, Subjects.FRENCH, Subjects.MATHEMATICS,
                           Subjects.PHYSICS_AND_CHEMISTRY, Subjects.LIFE_AND_EARTH_NATURAL_SCIENCES,
                           Subjects.HISTORY_AND_GEOGRAPHY, Subjects.ISLAMIC_EDUCATION, Subjects.PHILOSOPHY,
                           Subjects.PHYSICAL_EDUCATION, Subjects.COMPUTER_SCIENCE};

        switch (schoolLevel) {
            case HIGH_SCHOOL -> subjectChoice.setItems(FXCollections.observableArrayList(high));
            case MIDDLE_SCHOOL -> subjectChoice.setItems(FXCollections.observableArrayList(middle));
            case ELEMENTARY_SCHOOL -> subjectChoice.setItems(FXCollections.observableArrayList(elementary));
            default -> System.out.println();
        }
    }

    @FXML // This is for creating the account.
    private void createAccount(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (checkFieldsEmpty()) {
            alert.setTitle("Fields are empty");
            alert.setHeaderText("The fields are still empty.");
            alert.setContentText("Fill all the fields, so you can create a new account.");
            alert.showAndWait();
        }
        else if (getChoicesAreSelected()) {
            alert.setTitle("Choice");
            alert.setHeaderText("Choices are not selected.");
            alert.setContentText("Select your choices to specify yourself more.");
            alert.showAndWait();
        }
        else if (!teachersManager.checkInputName(nameField.getText())) {
            alert.setTitle("Name");
            alert.setHeaderText("The name must be only letters.");
            alert.setContentText("The name has numbers or symbols.");
            alert.showAndWait();
        }
        else if (!teachersManager.checkAge(ageField.getText())) {
            alert.setTitle("Age");
            alert.setHeaderText("The age must be only numbers");
            alert.setContentText("Cannot add letters or symbols into an age.");
            alert.showAndWait();
        }
        else if (!teachersManager.checkValidAge(Integer.parseInt(ageField.getText()))) {
            alert.setTitle("Age");
            alert.setHeaderText("The age is not as expected");
            alert.setContentText("The age must  be only between 20 and 40.");
            alert.showAndWait();
        }
        else if (!teachersManager.checkMobilePhone(mobilePhoneField.getText())) {
            alert.setTitle("Mobile phone");
            alert.setHeaderText("The mobile number must be only numbers");
            alert.setContentText("The mobile number cannot have letters or symbols.");
            alert.showAndWait();
        }
        else if (!teachersManager.passwordLength(passwordField.getText())) {
            alert.setTitle("Password");
            alert.setHeaderText("The password is too long or too short.");
            alert.setContentText("The password's length must be between 6 and 15.");
            alert.showAndWait();
        }
        else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            alert.setTitle("Password");
            alert.setHeaderText("The passwords are not the same.");
            alert.setContentText("Enter the same password in the confirming password field.");
            alert.showAndWait();
        }
        else {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String mobileNumber = mobilePhoneField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            Gender gender = genderChoice.getValue();
            Subjects subject = subjectChoice.getValue();
            SchoolsLevel school = schoolLevelChoice.getValue();
            City city = cityChoice.getValue();
            mainPage(name, age, password, gender, subject, school, city, mobileNumber, email, event);
        }
    }

    @FXML // This is for hiding the password.
    private void hidePassword() {
        passwordField = new PasswordField();
        passwordField.appendText(showPasswordField.getText());
        showPasswordField = new TextField();
        showPasswordField.setVisible(false);
        gridPane.add(passwordField,1,8);
        hidePasswordButton.setDisable(true);
        showPasswordButton.setDisable(false);
    }

    @FXML // This is for showing the password.
    private void showPassword() {
        showPasswordField = new TextField(passwordField.getText());
        passwordField.setVisible(false);
        showPasswordField.setVisible(true);
        gridPane.add(showPasswordField,1,8);
        hidePasswordButton.setDisable(false);
        showPasswordButton.setDisable(true);
    }

    @FXML // This is for clearing the fields.
    private void clear() {
        nameField.clear();
        ageField.clear();
        mobilePhoneField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        genderChoice.setValue(null);
        schoolLevelChoice.setValue(null);
        cityChoice.setValue(null);
        subjectChoice.setValue(null);
        showPasswordButton.setDisable(false);
        hidePasswordButton.setDisable(false);
    }

    private void mainPage(String name, int age, String password, Gender gender, Subjects subject, SchoolsLevel school,
                          City city, String phoneNumber, String email, ActionEvent event) throws IOException {

        Teacher teacher = new Teacher(name, age, password, subject, school, gender, city, phoneNumber, email);
        teachersManager.saveUser(teacher);
        Settings settings = saveDefaultSettings(teachersManager.getUserID());

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Scenes/HomeScene.fxml")));
        Parent parent = loader.load();
        Home home = loader.getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        home.setSettings(settings);
        home.setFilesManager(filesManager);
        home.setStudentsManager(studentsManager);
        home.setTeachersManager(teachersManager);
        home.setSettingsSQLite(settingsSQLite);
        home.setTeacher(teacher);

        Scene scene = new Scene(parent);
        String css = Objects.requireNonNull(this.getClass().getResource("/styles/HomeStyle.css")).toExternalForm();
        scene.getStylesheets().add(css);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/school.png")));
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.centerOnScreen();
        stage.getIcons().add(image);
        stage.show();
    }

    @FXML // This is for returning to the login screen.
    private void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/scenes/LoginScene.fxml")));
        Parent parent = loader.load();
        Login login = loader.getController();

        login.setFilesManager(filesManager);
        login.setStudentsManager(studentsManager);
        login.setTeachersManager(teachersManager);
        login.setSettingsSQLite(settingsSQLite);

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

    private boolean checkFieldsEmpty() {
        return nameField.getText().isEmpty() || ageField.getText().isEmpty() || emailField.getText().isEmpty() ||
               mobilePhoneField.getText().isEmpty() || passwordField.getText().isEmpty();
    }

    private boolean getChoicesAreSelected() {
        return genderChoice.getValue() == null || subjectChoice.getValue() == null ||
                cityChoice.getValue() == null || schoolLevelChoice.getValue() == null;
    }

    private Settings saveDefaultSettings(int userId) {
        String programColor = "WHITE";
        String dashboard = "WHITE";
        String language = "en";
        Settings settings = new Settings(programColor,dashboard,language,false,userId);
        settingsSQLite.insertUserSettings(settings);
        return settings;
    }

    // These are for getting the database and model classes and services classes.
    public void setFilesManager(FilesManager filesManager) {
        this.filesManager = filesManager;
    }
    public void setStudentsManager(StudentsManager studentsManager) {
        this.studentsManager = studentsManager;
    }
    public void setTeachersManager(TeachersManager teachersManager) {
        this.teachersManager = teachersManager;
    }
    public void setSettingsSQLite(SettingsSQLite settingsSQLite) {
        this.settingsSQLite = settingsSQLite;
    }
}