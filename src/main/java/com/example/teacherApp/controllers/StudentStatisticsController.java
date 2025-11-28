package com.example.teacherApp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example.teacherApp.models.Student;

public class StudentStatisticsController {

    @FXML private Label studentFullNameLabel, studentPointLabel, highestGradeLabel, lowestGradeLabel,
            averageGradesLabel, gradesLessThanTenLabel, studentRateLabel, statusLabel, studentIDLabel;

    public void setStudent(Student student) {
        studentFullNameLabel.setText(student.getFull_name());
        studentPointLabel.setText(String.valueOf(student.getPoint()));
        highestGradeLabel.setText(String.valueOf(student.getHighestGrade()));
        lowestGradeLabel.setText(String.valueOf(student.getLowestGrade()));
        averageGradesLabel.setText(String.valueOf(student.getAverageGrades()));
        gradesLessThanTenLabel.setText(String.valueOf(student.getGradesLessThanTen()));
        studentRateLabel.setText(student.studentRate());
        statusLabel.setText(student.getStatus());
        studentIDLabel.setText(String.valueOf(student.getID()));
    }
}
