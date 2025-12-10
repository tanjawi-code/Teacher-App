package com.example.teacherApp.controllers;

import com.example.teacherApp.services.Statistics.StudentStatisticsService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example.teacherApp.models.Student;

public class StudentStatisticsController {

    @FXML private Label studentFullNameLabel, studentPointLabel, highestGradeLabel, lowestGradeLabel,
            averageGradesLabel, gradesLessThanTenLabel, studentRateLabel, statusLabel, studentIDLabel;

    public void setStudent(StudentStatisticsService studentStatisticsService, Student student) {
        // Personal data: name, point, and id.
        studentFullNameLabel.setText(student.getFull_name());
        studentPointLabel.setText(String.valueOf(student.getPoint()));
        studentIDLabel.setText(String.valueOf(student.getID()));

        // The student's statistics.
        highestGradeLabel.setText(String.valueOf(studentStatisticsService.getHighestGrade()));
        lowestGradeLabel.setText(String.valueOf(studentStatisticsService.getLowestGrade()));
        averageGradesLabel.setText(String.valueOf(studentStatisticsService.getAverageGrades()));
        gradesLessThanTenLabel.setText(String.valueOf(studentStatisticsService.getGradesLessThanTen()));
        studentRateLabel.setText(studentStatisticsService.studentRate());
        statusLabel.setText(studentStatisticsService.getStatus());
    }
}
