import javax.swing.*;
import java.sql.*;
import java.text.DecimalFormat;

public class StudentsSQLite {

    private final DataBase dataBase;

    StudentsSQLite(DataBase dataBase){
        this.dataBase = dataBase;
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
                "FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE"+
                ");";

        try(Connection connection = dataBase.getConnect(); Statement statement = connection.createStatement()){
            statement.execute(sql);
        }
        catch (Exception e){
            System.out.println("Problem in creating table");
        }
    }

    // This is for storing student's details in SQLite.
    void insertStudentsToTable(Student student){
        String sql = "INSERT INTO students(first_name, second_name, full_name, gender, age, grade_1, grade_2, "+
                     "grade_3, point, city, student_id, class_number, teacher_id)"+
                     "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
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

        try(Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
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
    Boolean deleteStudentFromTable(int id){
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
    void updateStudentGrades(String fullName, int studentID, double[] grades, int age, double point){
        String sql = "UPDATE students SET grade_1 = ?, grade_2 = ?, grade_3 = ?, age = ?, point = ? "+
                "WHERE full_name = ? AND student_id = ?";
        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDouble(1, grades[0]);
            statement.setDouble(2, grades[1]);
            statement.setDouble(3, grades[2]);
            statement.setInt(4, age);
            statement.setDouble(5,point);
            statement.setString(6, fullName);
            statement.setInt(7, studentID);
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Problem in updating student's grades in table.");
        }
    }

    void check(){
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
