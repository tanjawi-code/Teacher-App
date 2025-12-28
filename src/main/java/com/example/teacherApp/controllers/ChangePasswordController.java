package com.example.teacherApp.controllers;

import com.example.teacherApp.services.managers.TeachersManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import com.example.teacherApp.models.Teacher;

import java.util.Optional;

public class ChangePasswordController {

    @FXML private TextField oldPasswordField, newPasswordField, confirmNewPasswordField;

    public void setDialogPane(DialogPane parent, BorderPane borderPane, GridPane gridPane, Teacher teacher, TeachersManager teachersManager) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(parent);
        dialog.setTitle("Changing the user's password");

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (checkFieldsIsEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "The fields are empty",ButtonType.OK).showAndWait();
            }
            else if (!teachersManager.selectTeacher(teacher.getName(), oldPasswordField.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "The old password is incorrect",ButtonType.OK).showAndWait();
            }
            else if (!teachersManager.passwordLength(newPasswordField.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "The password length must be between 6 and 15",
                        ButtonType.OK).showAndWait();
            }
            else if (!compareNewPasswordAndConfirmPassword()) {
                new Alert(Alert.AlertType.INFORMATION, "The new password must be the same as the confirm password",
                        ButtonType.OK).showAndWait();
            }
            else {
                teachersManager.changeUserPassword(newPasswordField.getText(), teachersManager.getUserID());
                new Alert(Alert.AlertType.INFORMATION,"You have changed your password successfully",
                        ButtonType.OK).showAndWait();
            }
        }
        else if(result.isPresent() && result.get() == ButtonType.CANCEL) {
            borderPane.setCenter(gridPane);
        }
    }

    public boolean checkFieldsIsEmpty() {
        return oldPasswordField.getText().isEmpty() || newPasswordField.getText().isEmpty() ||
                confirmNewPasswordField.getText().isEmpty();
    }
    public boolean compareNewPasswordAndConfirmPassword() {
        return newPasswordField.getText().equals(confirmNewPasswordField.getText());
    }
}
