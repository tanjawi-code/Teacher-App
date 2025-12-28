package com.example.teacherApp.services.managers;

import com.example.teacherApp.dao.TeachersSQLite;
import com.example.teacherApp.models.Teacher;

public class TeachersManager {

    private final TeachersSQLite teachersSQLite;

    public TeachersManager(TeachersSQLite teachersSQLite) {
        this.teachersSQLite = teachersSQLite;
    }

    // Saving a teacher.
    public void saveUser(Teacher teacher){
        teachersSQLite.insertTeachersToTable(teacher);
    }

    // This is for selecting a teacher from database using name and password.
    public Boolean selectTeacher(String name, String password) {
        return teachersSQLite.selectTeacherFromTable(name, password);
    }

    // This is for checking the user email if the user forgets the password.
    public Boolean checkUserEmail(String email) {
        return teachersSQLite.checkUserAccountEmail(email);
    }

    // This is for getting the teacher data from the database.
    public Teacher getTeacher() {
        return teachersSQLite.getTeacherData();
    }

    // This is for getting the id of the teacher from the database.
    public int getUserID() {
        return teachersSQLite.getTeacherID();
    }

    // This is for changing the password of the user using database, and the user's name and id.
    public void changeUserPassword(String password, int id) {
        teachersSQLite.changePassword(password, id);
    }

    // This is for deleting the account of the user.
    public Boolean deleteUserAccount(int id) {
        return teachersSQLite.deleteAccount(id);
    }

    // Conditions of adding a user.
    public boolean checkInputName(String name){
        return name.matches("[a-zA-Z]+(\\s[a-zA-z]+)*");
    }

    public Boolean passwordLength(String password) {
        return password.length() <= 15 && password.length() >= 6;
    }

    public boolean checkAge(String age){
        return age.matches("\\d+");
    }

    public boolean checkMobilePhone(String mobile){
        return mobile.matches("\\d+");
    }

    public boolean checkValidAge(int age) {
        return age >= 20 && age <= 40;
    }

    public boolean checkValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$");
    }
}
