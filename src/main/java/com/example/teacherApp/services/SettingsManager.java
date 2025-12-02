package com.example.teacherApp.services;

import com.example.teacherApp.dao.SettingsSQLite;
import com.example.teacherApp.models.Settings;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import java.util.Optional;

public class SettingsManager {

    private final SettingsSQLite settingsSQLite;

    public SettingsManager(SettingsSQLite settingsSQLite) {
        this.settingsSQLite = settingsSQLite;
    }

    // This is for saving default settings of the user.
    public void userDefaultSettings(Settings settings) {
        settingsSQLite.insertUserSettings(settings);
    }

    // This is for getting the user's settings after opening the program.
    public Settings getUserSettings(int userId) {
        return settingsSQLite.getUserSettings(userId);
    }

    // This is for changing the background color (might be the whole program or the dashboard).
    public void changeUserBackgroundColor(String programColor, String dashboardColor, int userId) {
        settingsSQLite.changeBackgroundsColor(programColor, dashboardColor, userId);
    }

    // This is for hiding json recommendation.
    public void stopJsonRecommendation(boolean jsonRecommendation, int userid) {
        settingsSQLite.hideJsonRecommendation(jsonRecommendation, userid);
    }

    // This is for getting the file extension recommendation.
    public Optional<ButtonType> getRecommendation(CheckBox checkBox) {
        DialogPane dialogPane = new DialogPane();
        dialogPane.setHeaderText("We recommend you to have a file as json extension, example (students.json).");
        dialogPane.setContent(checkBox);
        dialogPane.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        dialogPane.getButtonTypes().add(ButtonType.OK);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Json recommendation");

        return dialog.showAndWait();
    }
}
