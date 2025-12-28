package com.example.teacherApp.controllers;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.services.managers.TeachersManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.example.teacherApp.models.Student;
import com.example.teacherApp.services.managers.StudentsManager;
import java.net.URL;
import java.util.ResourceBundle;

public class AddStudentsController implements Initializable {

    private TeachersManager teachersManager;
    private StudentsManager studentsManager;
    private ObservableList<Student> students;

    @FXML private TextField firstNameField, lastNameField, ageField, firstGradeField, secondGradeField, thirdGradeField;
    @FXML private ChoiceBox<Gender> genderChoice;
    @FXML private ChoiceBox<City> cityChoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderChoice.setItems(FXCollections.observableArrayList(Gender.values()));
        cityChoice.setItems(FXCollections.observableArrayList(City.values()));
    }

    @FXML // This is for checking the fields and choices.
    private void addStudent() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (checkFieldsAreEmpty()) {
            alert.setTitle("fields");
            alert.setHeaderText("The fields are empty.");
            alert.setContentText("Fill the fields, so you can add a student.");
            alert.showAndWait();
        }
        else if (getChoicesSelected()) {
            alert.setTitle("Choice");
            alert.setHeaderText("The choices are not selected.");
            alert.setContentText("select the choices of the students (gender and city).");
            alert.showAndWait();
        }
        else if (!studentsManager.checkName(firstNameField.getText(), lastNameField.getText())) {
            alert.setTitle("Student's name");
            alert.setHeaderText("The first name or second name.");
            alert.setContentText("The first name or last name has spaces, numbers or symbols.");
            alert.showAndWait();
        }
        else if (!studentsManager.checkAge(ageField.getText())) {
            alert.setTitle("Student's age");
            alert.setHeaderText("The age is not correct.");
            alert.setContentText("The age must be only numbers without letters, symbols or spaces.");
            alert.showAndWait();
        }
        else if (!studentsManager.checkValidAge(Integer.parseInt(ageField.getText()))) {
            alert.setTitle("Student's age");
            alert.setHeaderText("The student's age is unexpected.");
            alert.setContentText("The student's age cannot be under 15 or above 20.");
            alert.showAndWait();
        }
        else if (!studentsManager.checkGrade(firstGradeField.getText(), secondGradeField.getText(), thirdGradeField.getText())) {
            alert.setTitle("Student's grades");
            alert.setHeaderText("The student's grades are not correct.");
            alert.setContentText("The student's grades must be only numbers.");
            alert.showAndWait();
        }
        else if (!studentsManager.checkValidGrades(Double.parseDouble(firstGradeField.getText()),
                                                   Double.parseDouble(secondGradeField.getText()),
                                                   Double.parseDouble(thirdGradeField.getText()))) {
            alert.setTitle("Student's grades");
            alert.setHeaderText("The student's grades are not correct.");
            alert.setContentText("The student's grades must be between 0 and 20.");
            alert.showAndWait();
        }
        else if (studentsManager.checkNameIfRepeated(firstNameField.getText()+" "+lastNameField.getText())) {
            alert.setTitle("Student's name");
            alert.setHeaderText("The student is already in the table.");
            alert.setContentText("You have already added this student to the table");
            alert.showAndWait();
        }
        else {
            addStudentToTable();
            clearFields();
            alert.setTitle("The student");
            alert.setHeaderText("The student is added.");
            alert.setContentText("You have add the student successfully.");
            alert.showAndWait();
        }
    }

    // This is for adding teh student ot the table.
    private void addStudentToTable() {
        SimpleStringProperty firstName = new SimpleStringProperty(firstNameField.getText());
        SimpleStringProperty lastName = new SimpleStringProperty(lastNameField.getText());
        Student student = getStudent(firstName, lastName);
        student.setStudentTeacherID(teachersManager.getUserID());
        student.calculateGrades();
        student.GenerateStudentID();

        studentsManager.saveStudent(student);
        students.add(student);
    }

    private Student getStudent(SimpleStringProperty firstName, SimpleStringProperty lastName) {
        SimpleStringProperty fullName = new SimpleStringProperty(firstName.getValue()+" "+lastName.getValue());
        SimpleIntegerProperty age = new SimpleIntegerProperty(Integer.parseInt(ageField.getText()));
        SimpleDoubleProperty grade1 = new SimpleDoubleProperty(Double.parseDouble(firstGradeField.getText()));
        SimpleDoubleProperty grade2 = new SimpleDoubleProperty(Double.parseDouble(secondGradeField.getText()));
        SimpleDoubleProperty grade3 = new SimpleDoubleProperty(Double.parseDouble(thirdGradeField.getText()));
        Gender gender = genderChoice.getValue();
        City city = cityChoice.getValue();

        int count = 0;
        SimpleIntegerProperty classNumber = new SimpleIntegerProperty(studentsManager.increaseClassNumber(count));

        return new Student(firstName, lastName, fullName, age, grade1, grade2, grade3, gender, city, classNumber);
    }

    @FXML // This is for clearing the fields.
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        ageField.clear();
        firstGradeField.clear();
        secondGradeField.clear();
        thirdGradeField.clear();
        genderChoice.setValue(null);
        cityChoice.setValue(null);
    }

    private boolean checkFieldsAreEmpty(){
        return firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                ageField.getText().isEmpty() || firstGradeField.getText().isEmpty() ||
                secondGradeField.getText().isEmpty() || thirdGradeField.getText().isEmpty();
    }
    private boolean getChoicesSelected() {
        return genderChoice.getValue() == null || cityChoice.getValue() == null;
    }

    void setTeachersManager(TeachersManager teachersManager) {
        this.teachersManager = teachersManager;
    }
    void setStudentsManager(StudentsManager studentsManager) {
        this.studentsManager = studentsManager;
    }
    void setObservableList(ObservableList<Student> students) {
        this.students = students;
    }
}