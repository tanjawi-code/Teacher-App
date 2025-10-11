import javax.swing.*;
import java.sql.*;
import java.text.DecimalFormat;

public class StudentsSQLite {

    StudentsSQLite(){}

    // This is for connecting with database.
    public Connection getConnect(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:dataBase/school.db");
            connection.createStatement().execute("PRAGMA foreign_keys = ON;");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Couldn't connect","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return connection;
    }

    // This is for creating a table for students in SQLite.
    void createStudentsTable(){
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
                "FOREIGN KEY (teacher_id) REFERENCES teachers(id)"+
                ");";

        try(Connection connection = getConnect(); Statement statement = connection.createStatement()){
            statement.execute(sql);
        }
        catch (Exception e){
            System.out.println("Something went wrong in creating table of students.");
        }
    }

    // This is for storing student's details in SQLite.
    void insertStudentsToTable(Student student){
        String sql = "INSERT INTO students(first_name, second_name, full_name, gender, age, grade_1, grade_2, "+
                     "grade_3, point, city, student_id, class_number, teacher_id)"+
                     "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            double point = Double.parseDouble(decimalFormat.format(Double.parseDouble(String.valueOf(student.getStudentPoint()))));

            statement.setString(1, student.getFirstStudentName());
            statement.setString(2, student.getSecondStudentName());
            statement.setString(3, student.getFullName());
            statement.setString(4, student.getStudentGender().toString());
            statement.setInt(5, student.getStudentAge());
            statement.setDouble(6, student.getStudentGrades(0));
            statement.setDouble(7, student.getStudentGrades(1));
            statement.setDouble(8, student.getStudentGrades(2));
            statement.setDouble(9, point);
            statement.setString(10, student.getStudentCity().toString());
            statement.setInt(11, student.getStudentID());
            statement.setInt(12, student.getStudentClassNumber());
            statement.setInt(13, student.getStudentTeacherID());
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Problem in inserting students to table");
        }
    }

    // This is for getting the students.
    void getStudents(int id, StudentsManager manager){
        String sql = "SELECT * FROM students WHERE teacher_id = ?";

        try(Connection connection = getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            Student student;
            while(resultSet.next()){
                double[] grades = new double[3];
                String firstName = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                String fullName = resultSet.getString("full_name");
                Gender gender = Gender.valueOf(resultSet.getString("gender"));
                int age = resultSet.getInt("age");
                grades[0] = resultSet.getDouble("grade_1");
                grades[1] = resultSet.getDouble("grade_2");
                grades[2] = resultSet.getDouble("grade_3");
                double point = resultSet.getDouble("point");
                City city = City.valueOf(resultSet.getString("city"));
                int studentID = resultSet.getInt("student_id");
                int classNumber = resultSet.getInt("class_number");
                int studentTeacherID = resultSet.getInt("teacher_id");
                student = new Student(firstName,secondName,age,gender,studentID,grades,point,city,classNumber,fullName);
                student.setStudentTeacherID(studentTeacherID);
                manager.saveStudent(student);
            }
        }
        catch (Exception e){
            System.out.println("problem 1 ");
        }
    }

    // This is for searching for a student to get the student's id.
    int deleteStudent(String fullName, int studentID){
        String sql = "SELECT * FROM students WHERE full_name = ? AND student_id = ?";
        try (Connection connection = getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
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
    Boolean deleteStudentFromTable(int id){
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection connection = getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            int deleted = statement.executeUpdate();
            return deleted > 0;
        }
        catch (Exception e){
            System.out.println("Problem in confirming deleting");
            return false;
        }
    }
}
