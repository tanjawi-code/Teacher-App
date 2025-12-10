package com.example.teacherApp.models;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Random;

public class Student {

    private final SimpleStringProperty first_name;
    private final SimpleStringProperty last_name;
    private final SimpleStringProperty full_name;
    private final SimpleIntegerProperty age;
    private final SimpleDoubleProperty grade_1;
    private final SimpleDoubleProperty grade_2;
    private final SimpleDoubleProperty grade_3;
    private SimpleDoubleProperty point;
    private SimpleIntegerProperty ID;
    private final SimpleIntegerProperty class_number;
    private final Gender gender;
    private final City city;
    private int studentTeacherID;

    public Student(SimpleStringProperty firstName, SimpleStringProperty lastName, SimpleStringProperty fullName,
                   SimpleIntegerProperty age, SimpleDoubleProperty grade1, SimpleDoubleProperty grade2,
                   SimpleDoubleProperty grade3, Gender gender, City city, SimpleIntegerProperty classNumber) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.full_name = fullName;
        this.age = age;
        this.grade_1 = grade1;
        this.grade_2 = grade2;
        this.grade_3 = grade3;
        this.gender = gender;
        this.city = city;
        this.class_number = classNumber;
    }

    public Student(SimpleStringProperty firstName, SimpleStringProperty lastName, SimpleStringProperty fullName,
                   SimpleIntegerProperty age, SimpleDoubleProperty grade1, SimpleDoubleProperty grade2,
                   SimpleDoubleProperty grade3, SimpleDoubleProperty point, SimpleIntegerProperty ID,
                   SimpleIntegerProperty classNumber, Gender gender, City city, int studentTeacherID) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.full_name = fullName;
        this.age = age;
        this.grade_1 = grade1;
        this.grade_2 = grade2;
        this.grade_3 = grade3;
        this.point = point;
        this.class_number = classNumber;
        this.ID = ID;
        this.gender = gender;
        this.city = city;
        this.studentTeacherID = studentTeacherID;
    }

    // Getter of properties.
    public SimpleStringProperty first_nameProperty() {
        return first_name;
    }
    public SimpleStringProperty last_nameProperty() {
        return last_name;
    }
    public SimpleStringProperty full_nameProperty() {
        return full_name;
    }
    public SimpleIntegerProperty ageProperty() {
        return age;
    }
    public SimpleDoubleProperty grade_1Property() {
        return grade_1;
    }
    public SimpleDoubleProperty grade_2Property() {
        return grade_2;
    }
    public SimpleDoubleProperty grade_3Property() {
        return grade_3;
    }
    public SimpleDoubleProperty pointProperty() {
        return point;
    }
    public SimpleIntegerProperty IDProperty() {
        return ID;
    }
    public SimpleIntegerProperty class_numberProperty() {
        return class_number;
    }

    // Normal getters.
    public String getFirst_name() {
        return first_name.get();
    }
    public String getLast_name() {
        return last_name.get();
    }
    public String getFull_name() {
        return full_name.get();
    }
    public int getAge() {
        return age.get();
    }
    public double getGrade_1() {
        return grade_1.get();
    }
    public double getGrade_2() {
        return grade_2.get();
    }
    public double getGrade_3() {
        return grade_3.get();
    }
    public double getPoint() {
        return point.get();
    }
    public int getID() {
        return ID.get();
    }
    public int getClass_number() {
        return class_number.get();
    }
    public Gender getGender() {
        return gender;
    }
    public City getCity() {
        return city;
    }
    public int getStudentTeacherID(){
        return this.studentTeacherID;
    }

    // Setters
    public void setStudentTeacherID(int studentTeacherID) {
        this.studentTeacherID = studentTeacherID;
    }
    public void setGrade_1(double grade1) {
        this.grade_1.set(grade1);
    }
    public void setGrade_2(double grade2) {
        this.grade_2.set(grade2);
    }
    public void setGrade_3(double grade3) {
        this.grade_3.set(grade3);
    }

    // Calculating The grades of the students.
    public void calculateGrades(){
        this.point = new SimpleDoubleProperty();
        this.point.set( (grade_1.get() + grade_2.get() + grade_3.get()) / 3); // Calculating the grades.
   }

    // Generate ID.
    public void GenerateStudentID() {
        this.ID = new SimpleIntegerProperty();
        Random random  = new Random();
        this.ID.set(random.nextInt(100,1000));
    }
}
