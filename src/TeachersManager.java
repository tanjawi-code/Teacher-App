import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class TeachersManager {

    ArrayList<Teacher> teachers = new ArrayList<>();

    // Getters.
    String getTeacherName(int index){
        return teachers.get(index).getName();
    }
    int getTeacherAge(int index){
        return teachers.get(index).getAge();
    }
    String getTeacherPassword(int index){
        return teachers.get(index).getPassword();
    }
    teacherGender getTeacherGender(int index){
        return teachers.get(index).getGender();
    }
    schools getTeacherSchool(int index){
        return teachers.get(index).getSchool();
    }
    subjects getTeacherSubject(int index){
        return teachers.get(index).getSubject();
    }

    // Saving a teacher.
    void saveTeacher(Teacher teacher){
        teachers.add(new Teacher(teacher));
    }

    int teachersSize(){
        return teachers.size();
    }

    boolean isEmpty(){
        return teachers.isEmpty();
    }

    void removeTeacher(int index){
        teachers.remove(index);
    }

    // This is for connecting with database.
    public Connection getConnect(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:dataBase/school.db");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Couldn't connect","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return connection;
    }

    // This is for creating a table for teachers.
    void createTeachersTable(){
        String table = "CREATE TABLE IF NOT EXISTS teachers ("+
                "id INTEGER PRIMARY KEY,"+
                "name TEXT NOT NULL,"+
                "password TEXT NOT NULL,"+
                "gender TEXT,"+
                "subject TEXT,"+
                "school TEXT"+
                ")";
        try(Connection connection = getConnect(); Statement statement = connection.createStatement()){
            statement.execute(table);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Something went wrong","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // This is for storing teacher's details in SQLite.
    void insertTeachersToTable(Teacher teacher){
        String sql = "INSERT INTO teachers(name, password, gender, subject, school) VALUES(?, ?, ?, ?, ?)";

        try(Connection connection = getConnect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setString(2, teacher.getPassword());
            preparedStatement.setString(3, teacher.getGender().toString());
            preparedStatement.setString(4, teacher.getSubject().toString());
            preparedStatement.setString(5, teacher.getSchool().toString());
            preparedStatement.executeUpdate();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Something went wrong","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // This is taking the details from the table.
    Boolean selectTeacherFromTable(String name, String password){
        String sql = "SELECT * FROM teachers WHERE name = ? AND password = ?";
        try(Connection connection = getConnect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
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
        try(Connection connection = getConnect(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Couldn't log into account","Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
