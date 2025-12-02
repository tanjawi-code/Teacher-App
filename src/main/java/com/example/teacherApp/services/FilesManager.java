package com.example.teacherApp.services;

import com.example.teacherApp.models.Student;
import com.example.teacherApp.models.Teacher;
import com.example.teacherApp.repository.SaveClassStatisticsFile;
import com.example.teacherApp.repository.StudentRepositoryFile;
import com.example.teacherApp.dao.SavedFilesSQLite;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FilesManager {

    private final SavedFilesSQLite savedFilesSQLite;
    private final StudentRepositoryFile studentRepositoryFile = new StudentRepositoryFile();

    public FilesManager(SavedFilesSQLite savedFilesSQLite) {
        this.savedFilesSQLite = savedFilesSQLite;
    }

    // This is for getting the user file directory from the database.
    public String userFilePath(int userId, String filePth) {
        return savedFilesSQLite.getUserFilePath(userId, filePth);
    }

    // This is for saving students data in a json file.
    public void saveStudentsDataInFile(List<Student> students, File file, int userId) throws IOException {
        if (file != null) {
            studentRepositoryFile.saveAllStudents(students, file);
            savedFilesSQLite.insertFileToTable(file.getPath(), file.getName(), getFileExtension(file), userId);
        }
    }

    // This is for getting students from a file.
    public void getStudents(File file, int userid, ObservableList<Student> students, StudentsManager manager) throws IOException {
        students.addAll(studentRepositoryFile.getAllStudents(file, userid));

        for (Student student : students) {
            manager.saveStudent(student);
        }
    }

    // This si for saving the class statistics in a json file.
    public void saveStatisticsFile(File file, int userid, StudentsManager manager, ObservableList<Student> students, Teacher teacher) throws IOException {
        SaveClassStatisticsFile statisticsFile = new SaveClassStatisticsFile(manager, students, teacher);
        statisticsFile.saveClassStatistics(file);
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
