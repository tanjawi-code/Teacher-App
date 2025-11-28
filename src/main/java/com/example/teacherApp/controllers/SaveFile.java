package com.example.teacherApp.controllers;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.models.StudentJSON;
import com.example.teacherApp.services.FilesManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import javafx.collections.ObservableList;
import com.example.teacherApp.models.Student;

import java.io.*;
import java.util.ArrayList;

public class SaveFile {

    private final FilesManager filesManager;
    private final ObservableList<Student> students;

    SaveFile(FilesManager filesManager, ObservableList<Student> students){
        this.filesManager = filesManager;
        this.students = students;
    }

    public void saveStudentsFile(File file, int teacherId) throws IOException {
        String filePath = file.getPath();
        String fileName = file.getName();
        String fileType = "";

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            fileType = fileName.substring(dotIndex + 1);
        }

        // passing file details to database.
        filesManager.saveUserFile(filePath,fileName,fileType,teacherId);
        // Saving students into .json file.
        saveStudents(file);
    }

    private void saveStudents(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<StudentJSON> list = new ArrayList<>();

        for (Student student : students) {
            list.add(getStudentJSON(student, student.getFull_name(), student.getAge()));
        }

        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(file, list);
    }

    private static StudentJSON getStudentJSON(Student student, String name, int age) {
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
}
