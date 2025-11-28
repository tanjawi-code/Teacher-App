package com.example.teacherApp.dao;

import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import com.example.teacherApp.Enums.SchoolsLevel;
import com.example.teacherApp.Enums.Subjects;
import com.example.teacherApp.models.Teacher;
import java.sql.*;

public class TeachersSQLite {

    private int teacherID;
    private final DataBase dataBase;

    public TeachersSQLite(DataBase dataBase){
        this.dataBase = dataBase;
    }

    // This is for creating a table for teachers.
    public void createTeachersTable(){
        String table = "CREATE TABLE IF NOT EXISTS teachers ("+
                "id INTEGER PRIMARY KEY,"+
                "name TEXT NOT NULL,"+
                "password TEXT NOT NULL,"+
                "age TEXT,"+
                "gender TEXT,"+
                "subject TEXT,"+
                "school TEXT,"+
                "city TEXT,"+
                "phone TEXT,"+
                "email TEXT"+
                ")";
        try(Connection connection = dataBase.getConnect(); Statement statement = connection.createStatement()){
            statement.execute(table);
        }
        catch (Exception e){
            System.out.println("Something went wrong in creating table of teachers.");
        }
    }

    // This is for storing teacher's details in SQLite.
    public void insertTeachersToTable(Teacher teacher){
        String sql = "INSERT INTO teachers(name, password, age, gender, subject, school, city, phone, email)"+
                     " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = dataBase.getConnect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setString(2, teacher.getPassword());
            preparedStatement.setString(3, String.valueOf(teacher.getAge()));
            preparedStatement.setString(4, teacher.getGender().toString());
            preparedStatement.setString(5, teacher.getSubject().toString());
            preparedStatement.setString(6, teacher.getSchool().toString());
            preparedStatement.setString(7, teacher.getCity().toString());
            preparedStatement.setString(8, teacher.getPhoneNUmber());
            preparedStatement.setString(9, teacher.getEmail());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            teacherID = -1;
            if (rs.next()) {
                teacherID = rs.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("Something went wrong in inserting data.");
        }
    }

    // This is taking the details from the table.
    public Boolean selectTeacherFromTable(String name, String password) {
        String sql = "SELECT * FROM teachers WHERE name = ? AND password = ?";
        try(Connection connection = dataBase.getConnect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            teacherID = resultSet.getInt("id");
            return resultSet.next();
        }
        catch (Exception e){
            System.out.println("Connection is not working");
            return false;
        }
    }

    // This is checking username in the account if the user forgets the password.
    public Boolean checkUserAccountEmail(String email){
        String sql = "SELECT * FROM teachers WHERE email = ?";
        try(Connection connection = dataBase.getConnect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            teacherID = resultSet.getInt("id");
            return resultSet.next();
        }
        catch (Exception e){
            System.out.println("Couldn't know the account.");
            return false;
        }
    }

    // This is for getting teacher data.
    public Teacher getTeacherData(){
        String sql = "SELECT * FROM teachers WHERE id = ?";
        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,teacherID);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                int age = resultSet.getInt("age");
                Gender gender = Gender.valueOf(resultSet.getString("gender"));
                Subjects subject = Subjects.valueOf(resultSet.getString("subject"));
                SchoolsLevel school = SchoolsLevel.valueOf(resultSet.getString("school"));
                City city = City.valueOf(resultSet.getString("city"));
                String phoneNumber = resultSet.getString("phone");
                String email = resultSet.getString("email");
                return new Teacher(name,age,password,subject,school,gender,city,phoneNumber,email);
            }
            else {
                return null;
            }
        }
        catch (Exception e){
            System.out.println("Problem in getting teacher data");
            return null;
        }
    }

    // This is for getting the teacher id.
    public int getTeacherID(){
        return this.teacherID;
    }

    // This is for changing user's password.
    public void changePassword(String password, int id){
        String sql = "UPDATE teachers SET password = ? WHERE id = ?";
        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, password);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Problem in changing password.");
        }
    }

    // This is for deleting the user's account.
    public Boolean deleteAccount(int id){
        String sql = "DELETE FROM teachers WHERE id = ?";
        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            int deleted = statement.executeUpdate();
            return deleted > 0;
        }
        catch (Exception e){
            System.out.println("Problem in deleting user's account");
            return false;
        }
    }
}
