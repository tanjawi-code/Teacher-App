package com.example.teacherApp.controllers;

import com.example.teacherApp.models.Settings;
import com.example.teacherApp.services.managers.SettingsManager;
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

    @FXML private ChoiceBox<String> programBackgroundColor, dashboardBackgroundColor, choiceBoxExceptDashboard;

    private BorderPane borderPane;
    private AnchorPane leftAnchorPane;
    private SettingsManager settingsManager;
    private Settings settings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] colors = {"WHITE","GRAY","BLACK"};
        String[] dashboardColors = {"WHITE","GRAY","BLACK","BLUE","RED","ORANGE","GREEN","YELLOW","PINK","PURPLE","BROWN"};
        programBackgroundColor.setItems(FXCollections.observableArrayList(colors));
        dashboardBackgroundColor.setItems(FXCollections.observableArrayList(dashboardColors));
        choiceBoxExceptDashboard.setItems(FXCollections.observableArrayList(colors));
    }

    public void setDialogPane(int userId, DialogPane dialogPane) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Theme");

        Optional<ButtonType> result = dialog.showAndWait();

        // This is for colors: 1 for whole program, 2 for dashboard only, 3 for whole program except dashboard.
        String[] colors = {programBackgroundColor.getValue(), dashboardBackgroundColor.getValue(), choiceBoxExceptDashboard.getValue()};

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (getChoicesAllSelected() || getTwoChoicesSelected()) {
                new Alert(Alert.AlertType.INFORMATION,"Cannot choose three or two choices in the same time",ButtonType.OK).
                        showAndWait();
            }
            else if (programBackgroundColor.getValue() != null) {
                getWholeProgramColor(userId, colors);
            }
            else if (dashboardBackgroundColor.getValue() != null) {
                getDashboardColor(userId, colors);
            }
            else if (choiceBoxExceptDashboard.getValue() != null) {
                getExceptDashboard(userId, colors);
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"Choices are not selected",ButtonType.OK).showAndWait();
            }
        }
    }

    // This is for checking if all choices are selected.
    public boolean getChoicesAllSelected() {
        return programBackgroundColor.getValue() != null && dashboardBackgroundColor.getValue() != null &&
                choiceBoxExceptDashboard.getValue() !=null;
    }

    // This is for checking if the user clicks two choices.
    public boolean getTwoChoicesSelected() {
        return programBackgroundColor.getValue() != null && dashboardBackgroundColor.getValue() != null ||
                programBackgroundColor.getValue() != null && choiceBoxExceptDashboard.getValue() != null ||
                choiceBoxExceptDashboard.getValue() != null && dashboardBackgroundColor.getValue() != null;
    }

    // Changing the whole color of the program.
    public void getWholeProgramColor(int userId, String[] colors) {
        settingsManager.changeUserBackgroundColor(colors[0], colors[0], userId);
        settings.setProgramBackground(colors[0]);
        settings.setDashboardBackground(colors[0]);

        borderPane.setBackground(new Background(new BackgroundFill(Color.valueOf(colors[0]), CornerRadii.EMPTY,
                Insets.EMPTY)));
        leftAnchorPane.setBackground(new Background(new BackgroundFill(Color.valueOf(colors[0]),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    // This is for getting the color of dashboard.
    public void getDashboardColor(int userId, String[] colors) {
        settingsManager.changeUserBackgroundColor(settings.getProgramBackground(), colors[1], userId);
        settings.setDashboardBackground(colors[1]);

        leftAnchorPane.setBackground(new Background(new BackgroundFill(Color.valueOf(colors[1]),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    // This is for getting the program background color except the dashboard.
    public void getExceptDashboard(int userId, String[] colors) {
        settingsManager.changeUserBackgroundColor(colors[2], settings.getDashboardBackground(), userId);
        settings.setProgramBackground(colors[2]);

        borderPane.setBackground(new Background(new BackgroundFill(Color.valueOf(colors[2]), CornerRadii.EMPTY,
                Insets.EMPTY)));
        leftAnchorPane.setBackground(new Background(new BackgroundFill(Color.valueOf(settings.getDashboardBackground()),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    // Setters.
    public void setSettings(Settings settings) {this.settings = settings;}
    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }
    public void setContainers(BorderPane borderPane, AnchorPane leftAnchorPane) {
        this.borderPane = borderPane;
        this.leftAnchorPane = leftAnchorPane;
    }
}
