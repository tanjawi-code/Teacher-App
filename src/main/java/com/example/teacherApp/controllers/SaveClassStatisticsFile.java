package com.example.teacherApp.controllers;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.models.ClassStatistics;
import com.example.teacherApp.models.Student;
import com.example.teacherApp.models.Teacher;
import com.example.teacherApp.services.FilesManager;
import com.example.teacherApp.services.StudentsManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaveClassStatisticsFile {

    private final Teacher teacher;
    private final StudentsManager studentsManager;
    private final ObservableList<Student> students;
    private final FilesManager filesManager;

    public SaveClassStatisticsFile(StudentsManager studentsManager, ObservableList<Student> students, FilesManager filesManager, Teacher teacher) {
        this.students = students;
        this.studentsManager = studentsManager;
        this.filesManager = filesManager;
        this.teacher = teacher;
    }

    public void saveClassStatistics(int teacherID, File file) throws IOException {
        // Step 1: get students in every city.
        ArrayList<City> cities = new ArrayList<>(List.of(City.values()));
        HashMap<String, Integer> studentCity = new HashMap<>();
        studentsCities(cities, studentCity);

        // Step 2: get class statistics.
        ClassStatistics classStatistics = getClassStatistics(studentCity);

        // Step 3: Save class statistics in a json file.
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(file, classStatistics);

        // Step 4: get file extension, path, and name.
        String filePath = file.getPath();
        String fileName = file.getName();
        String fileType = "";

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            fileType = fileName.substring(dotIndex + 1);
        }

        filesManager.saveUserFile(filePath, fileName, fileType, teacherID);
    }

    // This is for getting the students in every city.
    private void studentsCities(ArrayList<City> cities, HashMap<String, Integer> studentCity) {
        for (City city : cities) {
            int studentsInEachCity = 0;
            for (Student student : students) {
                if (city == student.getCity()) {
                    studentsInEachCity++;
                }
            }
            studentCity.put(String.valueOf(city), studentsInEachCity);
        }
    }

    // This is for getting the class statistics.
    private ClassStatistics getClassStatistics(HashMap<String, Integer> studentCity) {
        String subject = String.valueOf(teacher.getSubject());
        int totalStudents = studentsManager.studentsSize();
        int males = studentsManager.getMalesNumber();
        int females = studentsManager.getFemalesNumber();
        int passed = studentsManager.getNumberOfPassedStudents();
        int failed = studentsManager.getNumberOfFailedStudents();
        double highestPoint = studentsManager.getHighestPoint();
        double lowestPoint = studentsManager.getLowestPoint();
        String averagePoint = String.valueOf(studentsManager.getClassAverage());
        String averageAge = String.valueOf(studentsManager.getAverageAge());
        String succuss = studentsManager.getSuccussRate();

        return new ClassStatistics(subject,totalStudents,passed,failed,males,females,averagePoint,averageAge,
                                   highestPoint,lowestPoint, succuss,studentCity);
    }
}
