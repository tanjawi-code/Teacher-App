package com.example.teacherApp.controllers;

import com.example.teacherApp.services.TeachersManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import com.example.teacherApp.models.Teacher;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class UserAccountController {

    private BorderPane borderPane;
    private Teacher teacher;
    private TeachersManager teachersManager;

    @FXML private GridPane gridPane;
    @FXML private Label userNameLabel, ageLabel, genderLabel, emailLabel, mobileNumberLabel, schoolLevelLabel,
                        subjectLabel, numberOfClassesLabel;

    @FXML // This is for changing the user's password.
    private void changePassword() throws IOException {
        String page = "ChangePasswordScene";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/scenes/"+page+".fxml")));
        DialogPane dialogPane = loader.load();

        ChangePasswordController changePasswordController = loader.getController();
        changePasswordController.setDialogPane(dialogPane, borderPane, gridPane, teacher, teachersManager);
    }

    @FXML // This is for deleting the user's account.
    private void deleteAccount() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Password");
        textInputDialog.setHeaderText("Enter your password below");
        textInputDialog.setContentText("Your password:");
        Optional<String> result = textInputDialog.showAndWait();

        if (result.isPresent()) {
            if (!result.get().isEmpty()) {
                if (teachersManager.selectTeacher(teacher.getName(), result.get())) {
                    confirmDeletion();
                }
                else {
                    new Alert(Alert.AlertType.INFORMATION, "Sorry, the password is incorrect",ButtonType.OK).showAndWait();
                }
            }
            else {
                new Alert(Alert.AlertType.INFORMATION, "The field is still empty",ButtonType.OK).showAndWait();
            }
        }
    }
    private void confirmDeletion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting account");
        alert.setHeaderText("Are you sure you want to delete your account?");
        String message = """
                Deleting your account will remove everything you have: students, classes, your profile,
                your data, and everything you have in your account. If you deleted your account you can not
                back to it, once you click on delete account, your account will be gone forever, So think
                carefully before clicking on delete account button.""";
        Label label = new Label(message);
        label.setWrapText(true);
        alert.getDialogPane().setContent(label);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (teachersManager.deleteUserAccount(teachersManager.getUserID())) {
                System.exit(0);
            }
            else {
                new Alert(Alert.AlertType.INFORMATION, "Could not delete the account",ButtonType.OK).showAndWait();
            }
        }
        else {
            alert.close();
        }
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        userNameLabel.setText(teacher.getName());
        ageLabel.setText(String.valueOf(teacher.getAge()));
        genderLabel.setText(String.valueOf(teacher.getGender()));
        emailLabel.setText(teacher.getEmail());
        mobileNumberLabel.setText(teacher.getPhoneNUmber());
        schoolLevelLabel.setText(String.valueOf(teacher.getSchool()));
        subjectLabel.setText(String.valueOf(teacher.getSubject()));
        numberOfClassesLabel.setText("1");
    }
    public void setTeachersManager(TeachersManager teachersManager) {
        this.teachersManager = teachersManager;
    }
}