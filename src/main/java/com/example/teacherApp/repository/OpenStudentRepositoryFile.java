package com.example.teacherApp.repository;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.interfaces.OpenFileAble;
import com.example.teacherApp.models.Student;
import com.example.teacherApp.models.StudentJSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenStudentRepositoryFile implements OpenFileAble {

    private final int userid;

    public OpenStudentRepositoryFile(int userid) {
        this.userid = userid;
    }

    @Override
    public List<Student> openFile(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<StudentJSON> list = objectMapper.readValue(file, new TypeReference<>(){});
        List<Student> savedStudents = new ArrayList<>();

        for (StudentJSON studentJSON : list) {
            savedStudents.add(getStudent(studentJSON, userid));
        }

        return savedStudents;
    }

    // This is for getting students, and displaying them in table.
    private Student getStudent(StudentJSON studentJSON, int teacherID) {
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