package com.example.teacherApp.services.managers;

import com.example.teacherApp.models.Student;
import com.example.teacherApp.models.Teacher;
import com.example.teacherApp.repository.OpenStudentRepositoryFile;
import com.example.teacherApp.repository.SaveClassStatisticsFile;
import com.example.teacherApp.repository.SaveStudentRepositoryFile;
import com.example.teacherApp.dao.SavedFilesSQLite;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FilesManager {

    private final SavedFilesSQLite savedFilesSQLite;

    public FilesManager(SavedFilesSQLite savedFilesSQLite) {
        this.savedFilesSQLite = savedFilesSQLite;
    }

    // This is for getting the user file directory from the database.
    public String userFilePath(int userId, String filePth) {
        return savedFilesSQLite.getUserFilePath(userId, filePth);
    }

    // This is for saving students data in a JSON file.
    public void saveStudentsDataInFile(List<Student> students, File file, int userId) throws IOException {
        if (file != null) {
            new SaveStudentRepositoryFile(students).saveFile(file);
            savedFilesSQLite.insertFileToTable(file.getPath(), file.getName(), getFileExtension(file), userId);
        }
    }

    // This is for getting students from a file.
    public void getStudents(File file, int userid, ObservableList<Student> students, StudentsManager manager) throws IOException {
        students.addAll(new OpenStudentRepositoryFile(userid).openFile(file));

        for (Student student : students) {
            manager.saveStudent(student);
        }
    }

    // This si for saving the class statistics in a JSON file.
    public void saveStatisticsFile(File file, int userid, StudentsManager manager, Teacher teacher) throws IOException {
        new SaveClassStatisticsFile(manager, teacher).saveFile(file);
        savedFilesSQLite.insertFileToTable(file.getPath(), file.getName(), getFileExtension(file), userid);
    }

    // Return file extension.
    public String getFileExtension(File file) {
        int dotIndex = file.getName().lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < file.getName().length() - 1) {
            return file.getName().substring(dotIndex + 1);
        }
        return null;
    }
}