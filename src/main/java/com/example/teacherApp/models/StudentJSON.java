package com.example.teacherApp.models;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;

public class StudentJSON {

    private String full_Name;
    private int age;
    private double first_Grade;
    private double second_Grade;
    private double third_Grade;
    private double point;
    private int id;
    private int class_Number;
    private Gender gender;
    private City city;

    public StudentJSON(){}

    public StudentJSON(String fullName, int age, double firstGrade, double secondGrade, double thirdGrade,
                       double point, int id, int classNumber, Gender gender, City city) {
        this.full_Name = fullName;
        this.age = age;
        this.first_Grade = firstGrade;
        this.second_Grade = secondGrade;
        this.third_Grade = thirdGrade;
        this.point = point;
        this.id = id;
        this.class_Number = classNumber;
        this.gender = gender;
        this.city = city;
    }

    public String getFull_Name() {
        return full_Name;
    }
    public int getAge() {
        return age;
    }
    public double getFirst_Grade() {
        return first_Grade;
    }
    public double getSecond_Grade() {
        return second_Grade;
    }
    public double getThird_Grade() {
        return third_Grade;
    }
    public double getPoint() {
        return point;
    }
    public int getId() {
        return id;
    }
    public int getClass_Number() {
        return class_Number;
    }
    public Gender getGender() {
        return gender;
    }
    public City getCity() {
        return city;
    }
}
