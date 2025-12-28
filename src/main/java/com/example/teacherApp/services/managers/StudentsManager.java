package com.example.teacherApp.services.managers;

import com.example.teacherApp.dao.StudentsSQLite;
import com.example.teacherApp.models.Student;
import com.example.teacherApp.services.Statistics.StudentStatisticsService;
import com.example.teacherApp.services.Statistics.StudentsStatisticsClassService;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class StudentsManager {

    private StudentsSQLite studentsSQLite;
    private final ArrayList<Student> students = new ArrayList<>();

    public StudentsManager(){}

    public StudentsManager(StudentsSQLite studentsSQLite) {
        this.studentsSQLite = studentsSQLite;
    }

    // Saving a student.
    public void saveStudent(Student s) {
        studentsSQLite.insertStudentsToTable(s);
        students.add(s);
    }

    // This is for saving students to the ArrayList if the user already has an account and students in the database.
    public void saveStudentsToArrayList(Student student) {
        students.add(student);
    }

    // This is for getting all the students from the database.
    public void getAllStudents(int id, StudentsManager manager, ObservableList<Student> observableList) {
        studentsSQLite.getStudents(id,manager,observableList);
    }

    // This is for updating the student's grades.
    public void updateGrades(String fullName, int studentId, double[] grades, double point){
        studentsSQLite.updateStudentGrades(fullName, studentId, grades, point);
    }

    // This is for deleting the student.
    public Boolean deleteStudent(int id) {
        return studentsSQLite.deleteStudentFromTable(id);
    }

    // This is for getting the student id from the database.
    public int studentDataBaseId(String fullName, int studentId) {
        return studentsSQLite.getStudentDataBaseId(fullName,studentId);
    }

    // Delete all students from database.
    public void deleteAllStudentsFromList() {
        studentsSQLite.deleteAllStudents();
    }

    // The size of the students.
    public int studentsSize() {
        return students.size();
    }

    // This is for increasing the class number.
    public int increaseClassNumber(int count) {
        for(int x = 0; x<students.size(); x++) {
            count++;
        }
        return  count;
    }

    // This is for getting the service statistics of the students class.
    public StudentsStatisticsClassService getStudentsStatisticsService() {
        return new StudentsStatisticsClassService(students);
    }

    // This is for getting a student statistics.
    public StudentStatisticsService getStudentStatistics(Student student) {
        return new StudentStatisticsService(student);
    }

    // Get a student if it's in the list using the student's name.
    public Student getStudentIsInTable(String name) {
        for (Student student : students) {
            String fullName = student.getFull_name().toLowerCase();
            if (fullName.equals(name.toLowerCase())) {
                return student;
            }
        }
        return null;
    }

    // Get the list of student.
    public ArrayList<Student> getStudents() {
        return students;
    }

    // These are conditions for adding a student or editing grades.
    public boolean checkName(String firstName, String lastName) {
        return firstName.matches("[a-zA-Z]+") && lastName.matches("[a-zA-Z]+");
    }
    public boolean checkAge(String age) {
        return age.matches("\\d+");
    }
    public boolean checkValidAge(int age) {
        return age >= 15 && age <= 20;
    }
    public Boolean checkGrade(String grade1, String grade2, String grade3) {
        return grade1.matches("\\d+(\\.\\d+)?") && grade2.matches("\\d+(\\.\\d+)?") && grade3.matches("\\d+(\\.\\d+)?");
    }
    public Boolean checkValidGrades(double grade1, double grade2, double grade3){
        return grade1 >= 0 && grade1 <= 20 && grade2 >= 0 && grade2 <= 20 && grade3 >= 0 && grade3 <= 20;
    }
    public boolean checkNameIfRepeated(String fullName) {
        for (Student student : students) {
            if (student.getFull_name().toLowerCase().equals(fullName)) {
                return true;
            }
        }
        return false;
    }
}