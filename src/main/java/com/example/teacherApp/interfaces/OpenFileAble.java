package com.example.teacherApp.interfaces;

import com.example.teacherApp.models.Student;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface OpenFileAble {

    List<Student> openFile(File file) throws IOException;
}
