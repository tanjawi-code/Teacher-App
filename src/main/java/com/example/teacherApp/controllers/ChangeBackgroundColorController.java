package com.example.teacherApp.controllers;

import com.example.teacherApp.models.Settings;
import com.example.teacherApp.services.SettingsManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChangeBackgroundColorController implements Initializable {

    @FXML private ChoiceBox<String> programBackgroundColor, dashboardBackgroundColor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] colors = {"WHITE","GRAY","BLACK"};
        String[] dashboardColors = {"WHITE","GRAY","BLACK","BLUE","RED","ORANGE","GREEN","YELLOW","PINK","PURPLE","BROWN"};
        programBackgroundColor.setItems(FXCollections.observableArrayList(colors));
        dashboardBackgroundColor.setItems(FXCollections.observableArrayList(dashboardColors));
    }

    public void setDialogPane(SettingsManager settingsManager, int userId, DialogPane dialogPane,
                              BorderPane borderPane, AnchorPane leftAnchorPane, Settings settings) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Theme");

        Optional<ButtonType> result = dialog.showAndWait();
        String programColor = programBackgroundColor.getValue();
        String dashboard = dashboardBackgroundColor.getValue();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (programBackgroundColor.getValue() != null && dashboardBackgroundColor.getValue() != null) {
                new Alert(Alert.AlertType.INFORMATION,"Cannot choose two choices in the same time",ButtonType.OK).
                        showAndWait();
            }
            else if (programBackgroundColor.getValue() != null) {
                settingsManager.changeUserBackgroundColor(programColor, programColor, userId);
                settings.setProgramBackground(programColor);
                settings.setDashboardBackground(programColor);

                borderPane.setBackground(new Background(new BackgroundFill(Color.valueOf(programColor),
                        CornerRadii.EMPTY, Insets.EMPTY)));
                leftAnchorPane.setBackground(new Background(new BackgroundFill(Color.valueOf(programColor),
                        CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else if (dashboardBackgroundColor.getValue() != null) {
                settingsManager.changeUserBackgroundColor(settings.getProgramBackground(), dashboard, userId);
                settings.setDashboardBackground(dashboard);

                leftAnchorPane.setBackground(new Background(new BackgroundFill(Color.valueOf(dashboard),
                        CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"Choices are not selected",ButtonType.OK).showAndWait();
            }
        }
    }
}
