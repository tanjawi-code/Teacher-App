package com.example.teacherApp.controllers;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.models.Student;
import com.example.teacherApp.models.StudentJSON;
import com.example.teacherApp.services.StudentsManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GetFile  {

    private final ObservableList<Student> students;
    private final StudentsManager studentsManager;

    public GetFile(ObservableList<Student> students, StudentsManager studentsManager) {
        this.students = students;
        this.studentsManager = studentsManager;
    }

    public void getStudentsFile(File file, int teacherID) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<StudentJSON> list = objectMapper.readValue(file, new TypeReference<>(){});

        // Moving data to observable list of the table (Class Student).

        for (StudentJSON studentJSON : list) {
            students.add(getStudentToTable(studentJSON, teacherID));
        }

        // Adding students to SQLite.
        for (Student student : students) {
            studentsManager.saveStudent(student);
        }
    }

    private Student getStudentToTable(StudentJSON studentJSON, int teacherID) {
        SimpleStringProperty fullName = new SimpleStringProperty(studentJSON.getFull_Name());
        SimpleStringProperty firstname = new SimpleStringProperty(getStudentFirstName(studentJSON.getFull_Name()));
        SimpleStringProperty lastname = new SimpleStringProperty(getStudentLastName(studentJSON.getFull_Name()));
        SimpleIntegerProperty age = new SimpleIntegerProperty(studentJSON.getAge());
        SimpleIntegerProperty studentID = new SimpleIntegerProperty(studentJSON.getId());
        SimpleDoubleProperty grade1 = new SimpleDoubleProperty(studentJSON.getFirst_Grade());
        SimpleDoubleProperty grade2 = new SimpleDoubleProperty(studentJSON.getSecond_Grade());
        SimpleDoubleProperty grade3 = new SimpleDoubleProperty(studentJSON.getThird_Grade());
        SimpleDoubleProperty point = new SimpleDoubleProperty(studentJSON.getPoint());
        SimpleIntegerProperty classNumber = new SimpleIntegerProperty(studentJSON.getClass_Number());
        Gender gender = studentJSON.getGender();
        City city = studentJSON.getCity();
        return new Student(firstname, lastname, fullName, age, grade1, grade2, grade3, point, studentID,
                            classNumber,gender, city, teacherID);
    }

    // Getting the first word of the student (first name).
    private String getStudentFirstName(String fullName) {
        int space = fullName.indexOf(" ");
        return fullName.substring(0,space);
    }

    // Getting the second word of the student (last name).
    private String getStudentLastName(String fullName) {
        int space = fullName.indexOf(" ");
        return fullName.substring(space+1);
    }
}
