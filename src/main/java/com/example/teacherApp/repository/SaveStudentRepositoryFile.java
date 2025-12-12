package com.example.teacherApp.repository;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.interfaces.SaveFileAble;
import com.example.teacherApp.models.Student;
import com.example.teacherApp.models.StudentJSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveStudentRepositoryFile implements SaveFileAble {

    private final List<Student> students;

    public SaveStudentRepositoryFile(List<Student> students) {
        this.students = students;
    }

    @Override
    public void saveFile(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<StudentJSON> list = new ArrayList<>();

        for (Student student : students) {
            list.add(getStudentJSON(student, student.getFull_name(), student.getAge()));
        }

        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(file, list);
    }

    // This is for storing students in a JSON file using StudentJSON.
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
}
