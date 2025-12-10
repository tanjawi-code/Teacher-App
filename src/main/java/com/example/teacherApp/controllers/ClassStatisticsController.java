package com.example.teacherApp.controllers;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.services.Statistics.StudentsStatisticsClassService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import com.example.teacherApp.models.Student;
import com.example.teacherApp.models.Teacher;
import com.example.teacherApp.services.StudentsManager;
import java.net.URL;
import java.util.ResourceBundle;

public class ClassStatisticsController implements Initializable {

    @FXML private Label subjectLabel, totalStudentsLabel, malesLabel, femalesLabel, passedLabel, failedLabel,
                  averagePointsLabel, averageAgeLabel, highestPointLabel, lowestPointLabel, succussRateLabel,
                  studentsNumberOfCity;

    @FXML private ChoiceBox<City> cityChoiceBox;
    private int numberOfStudentsInCity = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cityChoiceBox.setItems(FXCollections.observableArrayList(City.values()));
    }

    public void setStudentsManager(Teacher teacher, StudentsManager manager, ObservableList<Student> students) {
        StudentsStatisticsClassService statisticsService = manager.getStudentsStatisticsService();

        subjectLabel.setText(String.valueOf(teacher.getSubject()));
        totalStudentsLabel.setText(String.valueOf(manager.studentsSize()));
        malesLabel.setText(String.valueOf(statisticsService.getMalesNumber()));
        femalesLabel.setText(String.valueOf(statisticsService.getFemalesNumber()));
        passedLabel.setText(String.valueOf(statisticsService.getNumberOfPassedStudents()));
        failedLabel.setText(String.valueOf(statisticsService.getNumberOfFailedStudents()));
        averagePointsLabel.setText(String.valueOf(statisticsService.getClassAverage()));
        averageAgeLabel.setText(String.valueOf(statisticsService.getAverageAge()));
        highestPointLabel.setText(String.valueOf(statisticsService.getHighestPoint()));
        lowestPointLabel.setText(String.valueOf(statisticsService.getLowestPoint()));
        succussRateLabel.setText(statisticsService.getSuccussRate());

        studentsInCities(students);
    }

    // This is for knowing how many students in each city.
    public void studentsInCities(ObservableList<Student> students) {
        cityChoiceBox.getSelectionModel().selectedItemProperty().addListener
                (((_, _, newValue) -> {
                    for (Student s : students) {
                        if (s.getCity() == newValue) {
                            numberOfStudentsInCity++;
                        }
                    }
                    studentsNumberOfCity.setText("Students number: "+ numberOfStudentsInCity);
                    numberOfStudentsInCity = 0;
                }));
    }
}
