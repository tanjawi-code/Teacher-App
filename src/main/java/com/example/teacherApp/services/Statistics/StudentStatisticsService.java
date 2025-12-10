package com.example.teacherApp.services.Statistics;

import com.example.teacherApp.models.Student;

import java.util.ArrayList;
import java.util.Collections;

public class StudentStatisticsService {

    public Student student;

    public StudentStatisticsService(Student student) {
        this.student = student;
    }

    // This is for getting student average grades.
    public double getAverageGrades() {
        return student.getPoint() / 3;
    }

    // This is for getting the highest grade.
    public double getHighestGrade() {
        ArrayList<Double> grades = new ArrayList<>();
        grades.add(student.getGrade_1());
        grades.add(student.getGrade_2());
        grades.add(student.getGrade_3());
        return Collections.max(grades);
    }

    // This is for getting the lowest grade.
    public double getLowestGrade() {
        ArrayList<Double> grades = new ArrayList<>();
        grades.add(student.getGrade_1());
        grades.add(student.getGrade_2());
        grades.add(student.getGrade_3());
        return Collections.min(grades);
    }

    // This is for giving a rate for teh student.
    public String studentRate() {
        if (student.getPoint() >= 18) {
            return "Excellent";
        }
        else if (student.getPoint() >= 14) {
            return "Very good";
        }
        else if (student.getPoint() >= 10) {
            return "Good";
        }
        else {
            return "Work Harder next time";
        }
    }

    // This is for knowing how many grades less than 10.
    public int getGradesLessThanTen() {
        int count = 0;
        if (student.getGrade_1() <= 9.99) {
            count++;
        }
        if (student.getGrade_2() <= 9.99) {
            count++;
        }
        if (student.getGrade_3() <= 9.99) {
            count++;
        }
        return count;
    }

    // This is for giving passed or failed.
    public String getStatus() {
        if (student.getPoint() >= 10) {
            return "Passed";
        }
        else {
            return "Failed";
        }
    }
}
