package com.example.teacherApp.repository;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.interfaces.StudentRepository;
import com.example.teacherApp.models.StudentJSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.example.teacherApp.models.Student;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryFile implements StudentRepository {

    public StudentRepositoryFile(){}

    @Override
    public List<Student> getAllStudents(File file, int userid) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<StudentJSON> list = objectMapper.readValue(file, new TypeReference<>(){});
        List<Student> students = new ArrayList<>();

        for (StudentJSON studentJSON : list) {
            students.add(getStudent(studentJSON, userid));
        }

        return students;
    }

    @Override
    public void saveAllStudents(List<Student> students, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<StudentJSON> list = new ArrayList<>();

        for (Student student : students) {
            list.add(getStudentJSON(student, student.getFull_name(), student.getAge()));
        }

        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(file, list);
    }

    // This is for storing students in a json file using StudentJSON.
    private StudentJSON getStudentJSON(Student student, String name, int age) {
        double grade1 = student.getGrade_1();
        double grade2 = student.getGrade_2();
        double grade3 = student.getGrade_3();
        double point = student.getPoint();
        int id = student.getID();
        int classNumber = student.getClass_number();
        Gender gender = student.getGender();
        City city = student.getCity();
        return new StudentJSON(name, age, grade1, grade2, grade3, point, id, classNumber, gender, city);
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
