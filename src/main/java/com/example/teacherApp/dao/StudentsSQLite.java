package com.example.teacherApp.dao;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import com.example.teacherApp.Enums.City;
import com.example.teacherApp.Enums.Gender;
import javafx.collections.ObservableList;
import com.example.teacherApp.models.Student;
import com.example.teacherApp.services.managers.StudentsManager;
import java.sql.*;
import java.text.DecimalFormat;

public class StudentsSQLite {

    private final DataBase dataBase;

    public StudentsSQLite(DataBase dataBase){
        this.dataBase = dataBase;
    }

    // This is for creating a table for students in SQLite.
    public void createStudentsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students ("+
                "id INTEGER PRIMARY KEY,"+
                "first_name TEXT NOT NULL,"+
                "second_name TEXT NOT NULL,"+
                "full_name TEXT NOT NULL,"+
                "gender TEXT,"+
                "age INTEGER,"+
                "grade_1 DOUBLE,"+
                "grade_2 DOUBLE,"+
                "grade_3 DOUBLE,"+
                "point DOUBLE,"+
                "city TEXT,"+
                "student_id INTEGER,"+
                "class_number INTEGER,"+
                "teacher_id INTEGER,"+
                "FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE"+
                ");";

        try(Connection connection = dataBase.getConnect(); Statement statement = connection.createStatement()){
            statement.execute(sql);
        }
        catch (Exception e){
            System.out.println("Problem in creating table of students.");
        }
    }

    // This is for storing student's details in SQLite.
    public void insertStudentsToTable(Student student) {
        String sql =
                "INSERT INTO students(first_name, second_name, full_name, gender, age, grade_1, grade_2, "+
                     "grade_3, point, city, student_id, class_number, teacher_id)"+
                     "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            double point = Double.parseDouble(decimalFormat.format(Double.parseDouble(String.valueOf(student.getPoint()))));

            statement.setString(1, student.getFirst_name());
            statement.setString(2, student.getLast_name());
            statement.setString(3, student.getFull_name());
            statement.setString(4, student.getGender().toString());
            statement.setInt(5, student.getAge());
            statement.setDouble(6, student.getGrade_1());
            statement.setDouble(7, student.getGrade_2());
            statement.setDouble(8, student.getGrade_3());
            statement.setDouble(9, point);
            statement.setString(10, student.getCity().toString());
            statement.setInt(11, student.getID());
            statement.setInt(12, student.getClass_number());
            statement.setInt(13, student.getStudentTeacherID());
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Problem in inserting students to table");
        }
    }

    // This is for getting the students.
    public void getStudents(int id, StudentsManager manager, ObservableList<Student> students) {
        String sql = "SELECT * FROM students WHERE teacher_id = ?";

        try(Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            Student student;
            while(resultSet.next()) {
                SimpleStringProperty firstName = new SimpleStringProperty(resultSet.getString("first_name"));
                SimpleStringProperty lastName = new SimpleStringProperty(resultSet.getString("second_name"));
                SimpleStringProperty fullName = new SimpleStringProperty(resultSet.getString("full_name"));
                SimpleIntegerProperty age = new SimpleIntegerProperty(resultSet.getInt("age"));
                SimpleDoubleProperty grade1 = new SimpleDoubleProperty(resultSet.getDouble("grade_1"));
                SimpleDoubleProperty grade2 = new SimpleDoubleProperty(resultSet.getDouble("grade_2"));
                SimpleDoubleProperty grade3 = new SimpleDoubleProperty(resultSet.getDouble("grade_3"));
                SimpleDoubleProperty point = new SimpleDoubleProperty(resultSet.getDouble("point"));
                SimpleIntegerProperty studentID = new SimpleIntegerProperty(resultSet.getInt("student_id"));
                SimpleIntegerProperty classNumber = new SimpleIntegerProperty(resultSet.getInt("class_number"));
                Gender gender = Gender.valueOf(resultSet.getString("gender"));
                City city = City.valueOf(resultSet.getString("city"));
                int studentTeacherID = resultSet.getInt("teacher_id");

                student = new Student(firstName,lastName,fullName,age,grade1,grade2,grade3,point,studentID,
                        classNumber,gender,city,studentTeacherID);
                students.add(student);
                manager.saveStudentsToArrayList(student);
            }
        }
        catch (Exception e){
            System.out.println("problem at getting students to SQLITE.");
        }
    }

    // This is for searching for a student to get the student's id.
    public int getStudentDataBaseId(String fullName, int studentID) {
        String sql = "SELECT * FROM students WHERE full_name = ? AND student_id = ?";
        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, fullName);
            statement.setInt(2, studentID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("id");
            }
            else {
                return -1;
            }
        }
        catch (Exception e){
            System.out.println("Problem in deleting student from table.");
            return -1;
        }
    }

    // This is for deleting the student after getting the id.
    public Boolean deleteStudentFromTable(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            int deleted = statement.executeUpdate();
            return deleted > 0;
        }
        catch (Exception e){
            System.out.println("Problem in confirming deleting");
            return false;
        }
    }

    // This is for updating student's grades.
    public void updateStudentGrades(String fullName, int studentID, double[] grades, double point){
        String sql = "UPDATE students SET grade_1 = ?, grade_2 = ?, grade_3 = ?, point = ? "+
                "WHERE full_name = ? AND student_id = ?";
        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDouble(1, grades[0]);
            statement.setDouble(2, grades[1]);
            statement.setDouble(3, grades[2]);
            statement.setDouble(4,point);
            statement.setString(5, fullName);
            statement.setInt(6, studentID);
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Problem in updating student's grades in table.");
        }
    }

    // This is for deleting all students.
    public void deleteAllStudents() {
        String sql = "DELETE FROM students";
        try (Connection connection = dataBase.getConnect(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
        catch (Exception e) {
            System.out.println("Problem at deleting all data in students table.");
        }
    }

    public void check(){
        String sql = " PRAGMA foreign_key_list(students);";

        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                System.out.println("Table : "+resultSet.getString("table")+"\n"+
                        "Column : "+resultSet.getString("from")+"\n"+
                        "Related : "+resultSet.getString("to")+"\n"+
                        "To delete : "+resultSet.getString("on_delete"));
            }
        }
        catch (Exception e) {
            System.out.println("Problem");
        }
    }
}
