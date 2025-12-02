package com.example.teacherApp.interfaces;

import com.example.teacherApp.models.Student;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface StudentRepository {

    List<Student> getAllStudents(File file ,int userid) throws IOException;
    void saveAllStudents(List<Student> students, File file) throws IOException;
}
