package com.example.teacherApp.controllers;

import com.example.teacherApp.services.managers.StudentsManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import com.example.teacherApp.models.Student;

public class EditStudentController {

    private StudentsManager studentsManager;
    private ObservableList<Student> students;
    private TableView<Student> tableView;
    private Student student;
    private AnchorPane anchorPane;
    private BorderPane borderPane;

    @FXML private TextField grade1, grade2, grade3;
    @FXML private Label studentNameLabel, studentPointLabel;

    @FXML // Confirming the updates of grades.
    private void submit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (!studentsManager.checkGrade(grade1.getText(), grade2.getText(), grade3.getText())) {
                 alert.setTitle("The student's grades");
                 alert.setHeaderText("The grades must be only numbers");
                 alert.setContentText("The grades cannot have letters, symbols, or spaces.");
                 alert.showAndWait();
        }
        else if (!studentsManager.checkValidGrades(Double.parseDouble(grade1.getText()),
                                                   Double.parseDouble(grade2.getText()),
                                                   Double.parseDouble(grade3.getText())) ) {
                 alert.setTitle("The student's grades");
                 alert.setHeaderText("The grades are not expected");
                 alert.setContentText("The must be between 0 and 20.");
                 alert.showAndWait();
        }
        else if (student.getGrade_1() == Double.parseDouble(grade1.getText()) &&
                 student.getGrade_2() == Double.parseDouble(grade2.getText()) &&
                 student.getGrade_3() == Double.parseDouble(grade3.getText())) {
                 alert.setTitle("The student's grades");
                 alert.setHeaderText("The grades are not changed");
                 alert.setContentText("Change at least one grade, so you can change the point.");
                 alert.showAndWait();
        }
        else {
            for (Student s : students) {
                if (s.getFull_name().equals(student.getFull_name())) {
                    s.setGrade_1(Double.parseDouble(grade1.getText()));
                    s.setGrade_2(Double.parseDouble(grade2.getText()));
                    s.setGrade_3(Double.parseDouble(grade3.getText()));
                    s.calculateGrades();

                    alert.setTitle("The student's grades");
                    alert.setHeaderText("The student's grades are updated.");
                    alert.setContentText("The student's point now is: "+s.getPoint());
                    alert.showAndWait();

                    studentPointLabel.setText(String.valueOf(s.getPoint()));
                    double[] grades = {s.getGrade_1(),s.getGrade_2(),s.getGrade_3()};
                    studentsManager.updateGrades(s.getFull_name(),s.getID(),grades,s.getPoint());
                    tableView.refresh();
                    break;
                }
            }
        }
    }

    @FXML
    private void cancel() {
        borderPane.setCenter(anchorPane);
    }

    public void setStudentsManager(StudentsManager studentsManager) {
        this.studentsManager = studentsManager;
    }
    public void setObservableList(ObservableList<Student> students) {
        this.students = students;
    }
    public void setTableView(TableView<Student> tableView) {
        this.tableView = tableView;
    }
    public void setStudent(Student student) {
        this.student = student;
        studentNameLabel.setText(student.getFull_name());
        studentPointLabel.setText(String.valueOf(student.getPoint()));
        grade1.setText(String.valueOf(student.getGrade_1()));
        grade2.setText(String.valueOf(student.getGrade_2()));
        grade3.setText(String.valueOf(student.getGrade_3()));
    }
    public void setHomePage(AnchorPane anchorPane, BorderPane borderPane) {
        this.anchorPane = anchorPane;
        this.borderPane = borderPane;
    }
}
