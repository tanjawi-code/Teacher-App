import javax.swing.*;
import java.sql.*;

public class TeachersSQLite {

    private int teacherID;
    private final DataBase dataBase;

    TeachersSQLite(DataBase dataBase){
        this.dataBase = dataBase;
    }

    // This is for creating a table for teachers.
    void createTeachersTable(){
        String table = "CREATE TABLE IF NOT EXISTS teachers ("+
                "id INTEGER PRIMARY KEY,"+
                "name TEXT NOT NULL,"+
                "password TEXT NOT NULL,"+
                "age TEXT,"+
                "gender TEXT,"+
                "subject TEXT,"+
                "school TEXT"+
                ")";
        try(Connection connection = dataBase.getConnect(); Statement statement = connection.createStatement()){
            statement.execute(table);
        }
        catch (Exception e){
            System.out.println("Something went wrong in creating table.");
        }
    }

    // This is for storing teacher's details in SQLite.
    void insertTeachersToTable(Teacher teacher){
        String sql = "INSERT INTO teachers(name, password, age, gender, subject, school) VALUES(?, ?, ?, ?, ?, ?)";

        try(Connection connection = dataBase.getConnect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setString(2, teacher.getPassword());
            preparedStatement.setString(3, String.valueOf(teacher.getAge()));
            preparedStatement.setString(4, teacher.getGender().toString());
            preparedStatement.setString(5, teacher.getSubject().toString());
            preparedStatement.setString(6, teacher.getSchool().toString());
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
    Boolean selectTeacherFromTable(String name, String password){
        String sql = "SELECT * FROM teachers WHERE name = ? AND password = ?";
        try(Connection connection = dataBase.getConnect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            teacherID = resultSet.getInt("id");
            return resultSet.next();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Couldn't log into account","Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // This is checking username in the account if the user forgets the password.
    Boolean checkUserAccountName(String name){
        String sql = "SELECT * FROM teachers WHERE name = ?";
        try(Connection connection = dataBase.getConnect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            teacherID = resultSet.getInt("id");
            return resultSet.next();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Couldn't log into account","Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // This is for getting teacher data.
    Teacher getTeacherData(){
        String sql = "SELECT * FROM teachers WHERE id = ?";
        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,teacherID);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                String password = resultSet.getString("password");
                int age = resultSet.getInt("age");
                teacherGender gender = teacherGender.valueOf(resultSet.getString("gender"));
                subjects subject = subjects.valueOf(resultSet.getString("subject"));
                schools school = schools.valueOf(resultSet.getString("school"));
                return new Teacher(name,age,password,subject,school,gender,id);
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
    int getTeacherID(){
        return this.teacherID;
    }

    // This is for changing user's password.
    void changePassword(String password, int id){
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
    Boolean deleteAccount(int id){
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
