package com.example.teacherApp.services;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.dao.StudentsSQLite;
import com.example.teacherApp.models.Student;
import javafx.collections.ObservableList;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

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

    // This is for getting the gender of the student.
    public City getCity(int index) {
        return students.get(index).getCity();
    }

    // This is for increasing the class number.
    public int increaseClassNumber(int count) {
        for(int x = 0; x<students.size(); x++) {
            count++;
        }
        return  count;
    }

    // This is for the class statistics.

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
