package com.example.teacherApp.controllers;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.services.filters.*;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import com.example.teacherApp.models.Student;
import java.net.URL;
import java.util.ResourceBundle;

public class FilterAndSortingController implements Initializable {

    private AnchorPane leftAnchorPane;
    private BorderPane borderPane;
    private FilteredList<Student> filteredList;

    @FXML private ChoiceBox<Integer> ageChoiceBox;
    @FXML private ChoiceBox<Gender> genderChoiceBox;
    @FXML private ChoiceBox<City> cityChoiceBox;
    @FXML private ChoiceBox<String> pointChoiceBox, statusChoiceBox;
    private Button editButton, deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderChoiceBox.setItems(FXCollections.observableArrayList(Gender.values()));
        cityChoiceBox.setItems(FXCollections.observableArrayList(City.values()));
        ageChoiceBox.getItems().addAll(15,16,17,18,19,20);
        statusChoiceBox.getItems().addAll("Passed","Failed");
        pointChoiceBox.getItems().addAll("0-4.99","5-9.99","10-14.99","15-17.99","18-20");
    }

    @FXML // Going back to the dashboard.
    private void cancel() {
        borderPane.setLeft(leftAnchorPane);
        filteredList.setPredicate(_ -> true);
        editButton.setDisable(false);
        deleteButton.setDisable(false);
    }

    @FXML
    private void refresh() {
        ageChoiceBox.setValue(null);
        genderChoiceBox.setValue(null);
        cityChoiceBox.setValue(null);
        pointChoiceBox.setValue(null);
        statusChoiceBox.setValue(null);
        filteredList.setPredicate(_ -> true);
    }

    public void setFilteredList(FilteredList<Student> filteredList) {
        this.filteredList = filteredList;

        new GenderFilter(genderChoiceBox).filter(filteredList);
        new CityFilter(cityChoiceBox).filter(filteredList);
        new AgeFilter(ageChoiceBox).filter(filteredList);
        new StatusFilter(statusChoiceBox).filter(filteredList);
        new PointFilter(pointChoiceBox).filter(filteredList);
    }
    public void setLeftAnchorPane(AnchorPane leftAnchorPane) {
        this.leftAnchorPane = leftAnchorPane;
    }
    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
    public void setDisabledButtons(Button editButton, Button deleteButton) {
        this.editButton = editButton;
        this.deleteButton = deleteButton;
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }
}
