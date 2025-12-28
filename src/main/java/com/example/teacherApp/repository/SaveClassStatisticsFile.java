package com.example.teacherApp.repository;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.interfaces.SaveFileAble;
import com.example.teacherApp.models.ClassStatistics;
import com.example.teacherApp.models.Student;
import com.example.teacherApp.models.Teacher;
import com.example.teacherApp.services.Statistics.StudentsStatisticsClassService;
import com.example.teacherApp.services.managers.StudentsManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaveClassStatisticsFile implements SaveFileAble {

    private final Teacher teacher;
    private final StudentsManager studentsManager;

    public SaveClassStatisticsFile(StudentsManager studentsManager, Teacher teacher) {
        this.studentsManager = studentsManager;
        this.teacher = teacher;
    }

    @Override
    public void saveFile(File file) throws IOException{
        // Step 1: get students in every city.
        ArrayList<City> cities = new ArrayList<>(List.of(City.values()));
        HashMap<String, Integer> studentCity = new HashMap<>();
        studentsCities(cities, studentCity);

        // Step 2: get class statistics.
        ClassStatistics classStatistics = getClassStatistics(studentCity);

        // Step 3: Save class statistics in a JSON file.
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(file, classStatistics);
    }

    // This is for getting the students in every city.
    private void studentsCities(ArrayList<City> cities, HashMap<String, Integer> studentCity) {
        ArrayList<Student> students = studentsManager.getStudents();

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
        StudentsStatisticsClassService statisticsService = studentsManager.getStudentsStatisticsService();

        String subject = String.valueOf(teacher.getSubject());
        int totalStudents = studentsManager.studentsSize();
        int males = statisticsService.getMalesNumber();
        int females = statisticsService.getFemalesNumber();
        int passed = statisticsService.getNumberOfPassedStudents();
        int failed = statisticsService.getNumberOfFailedStudents();
        double highestPoint = statisticsService.getHighestPoint();
        double lowestPoint = statisticsService.getLowestPoint();
        String averagePoint = String.valueOf(statisticsService.getClassAverage());
        String averageAge = String.valueOf(statisticsService.getAverageAge());
        String succuss = statisticsService.getSuccussRate();

        return new ClassStatistics(subject,totalStudents,passed,failed,males,females,averagePoint,averageAge,
                                   highestPoint,lowestPoint, succuss,studentCity);
    }
}
