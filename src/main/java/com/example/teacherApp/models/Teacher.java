package com.example.teacherApp.models;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.Enums.SchoolsLevel;
import com.example.teacherApp.Enums.Subjects;

public class Teacher {

    private final String name;
    private final int age;
    private final String password;
    private Subjects subject;
    private SchoolsLevel school;
    private String phoneNUmber;
    private String email;
    private Gender gender;
    private City city;

    public Teacher(){
        this.name = "Not found";
        this.age = 0;
        this.password = "******";
    }

    public Teacher(String name, int age, String password, Subjects subject, SchoolsLevel school, Gender gender,
                   City city, String phoneNUmber, String email) {
        this.name = name;
        this.age = age;
        this.password = password;
        this.subject = subject;
        this.school = school;
        this.gender = gender;
        this.city = city;
        this.phoneNUmber = phoneNUmber;
        this.email = email;
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getPassword() {
        return password;
    }
    public Subjects getSubject() {
        return subject;
    }
    public SchoolsLevel getSchool() {
        return school;
    }
    public Gender getGender() {
        return gender;
    }
    public City getCity() {
        return city;
    }
    public String getPhoneNUmber() {
        return phoneNUmber;
    }
    public String getEmail(){
        return email;
    }
}
