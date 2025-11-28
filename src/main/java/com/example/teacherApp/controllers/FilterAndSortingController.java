package com.example.teacherApp.controllers;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
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

        // Filtering by gender.
        filterByGender();

        // Filtering by city.
        filterByCity();

        // Filtering by age.
        filterByAge();

        // Filter by status.
        filterByStatus();

        // Filter by point.
        filterByPoint();
    }

    // Filters.
    private void filterByGender () {
        Runnable runnable = () -> filteredList.setPredicate(student -> {
            Gender gender = genderChoiceBox.getValue();
            return gender != null && student.getGender() == gender;
        });
        genderChoiceBox.valueProperty().addListener((_, _, _) -> runnable.run());
    }
    private void filterByAge() {
        Runnable runnable = () -> filteredList.setPredicate(student -> {
            Integer age = ageChoiceBox.getValue();
            return age != null && age == student.getAge();
        });
        ageChoiceBox.valueProperty().addListener((_, _,_) -> runnable.run());
    }
    private void filterByCity() {
        Runnable runnable = () -> filteredList.setPredicate(student -> {
            City city = cityChoiceBox.getValue();
            return city != null && student.getCity() == city;
        });
        cityChoiceBox.valueProperty().addListener((_, _, _) -> runnable.run());

    }
    private void filterByStatus() {
        Runnable runnable = () -> filteredList.setPredicate(student -> {
            String status = statusChoiceBox.getValue();

            if (status != null && status.equals("Passed") && student.getPoint() >= 10) {
                return true;
            }
            else return status != null && status.equals("Failed") && student.getPoint() <= 9.99;
        });
        statusChoiceBox.valueProperty().addListener((_,_,_) -> runnable.run());
    }
    private void filterByPoint() {
        Runnable runnable = () -> filteredList.setPredicate(student -> {
            String point = pointChoiceBox.getValue();
            double max = 0;
            double min = 0;

            if (point != null) {
                switch (point) {
                    case "18-20" : max = 20; min = 18; break;
                    case "15-17.99" : max = 17.99; min = 15; break;
                    case "10-14.99" : max = 14.99; min = 10; break;
                    case "5-9.99" : max = 9.99; min = 5; break;
                    case "0-4.99" : max = 4.99; min = 0; break;
                    default: System.out.println();
                }
                return student.getPoint() >= min && student.getPoint() <= max;
            }
            else {
                return false;
            }
        });

        pointChoiceBox.valueProperty().addListener((_,_,_) -> runnable.run());
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
