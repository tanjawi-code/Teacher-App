package com.example.teacherApp.services.Statistics;

import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.models.Student;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class StudentsStatisticsClassService {

    private final ArrayList<Student> students;

    public StudentsStatisticsClassService(ArrayList<Student> students) {
        this.students = students;
    }

    // Number of passed students.
    public int getNumberOfPassedStudents(){
        int passed = 0;
        for (Student student : students) {
            if (student.getPoint() >= 10) {
                passed++;
            }
        }
        return passed;
    }
    // Number of failed students.
    public int getNumberOfFailedStudents(){
        int failed = 0;
        for(Student student : students){
            if(student.getPoint() <= 9.99){
                failed++;
            }
        }
        return failed;
    }
    // Number of males.
    public int getMalesNumber(){
        int males = 0;
        for(Student student : students){
            if(student.getGender() == Gender.MALE){
                males++;
            }
        }
        return males;
    }
    // Number of females.
    public int getFemalesNumber(){
        int females = 0;
        for(Student student : students){
            if(student.getGender() == Gender.FEMALE){
                females++;
            }
        }
        return females;
    }
    // The general average of the class.
    public double getClassAverage(){
        double average = 0;
        for(Student student : students){
            average += student.getPoint();
        }
        average = average / students.size();

        return average;
    }
    // The general average of students' age.
    public double getAverageAge() {
        int age = 0;
        for (Student student : students) {
            age += student.getAge();
        }
        return (double) age / students.size();
    }
    // The succuss rate.
    public String getSuccussRate(){
        double percentage = (getNumberOfPassedStudents() * 100.0) / students.size();

        DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.format(percentage);
        String value = String.valueOf(decimalFormat.format(Double.parseDouble(String.valueOf(percentage))));
        value = value+"%";

        return value;
    }
    // The highest point.
    public double getHighestPoint(){
        ArrayList<Double> points = new ArrayList<>();
        for(Student student : students){
            points.add(student.getPoint());
        }
        return Collections.max(points);
    }
    // The lowest point.
    public double getLowestPoint(){
        ArrayList<Double> points = new ArrayList<>();
        for(Student student : students){
            points.add(student.getPoint());
        }
        return Collections.min(points);
    }
}
