import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SavedFilesSQLite {

    private final DataBase dataBase;

    SavedFilesSQLite(DataBase dataBase){
        this.dataBase = dataBase;
    }

    // Create a table for saving files of users.
    void createSavedFilesTeachersTable(){
        String sql = "CREATE TABLE IF NOT EXISTS files ("+
                "id INTEGER PRIMARY KEY,"+
                "file_path TEXT NOT NULL,"+
                "file_name TEXT NOT NULL,"+
                "file_type TEXT NOT NULL,"+
                "teacher_id INTEGER,"+
                "FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE"+
                ");";

        try (Connection connection = dataBase.getConnect(); Statement statement = connection.createStatement()){
            statement.execute(sql);
        }
        catch (Exception e){
            System.out.println("Problem is creating files to table.");
        }
    }

    // This is for putting a file of the user.
    void insertFileToTable(String filePath, String fileName, String fileType, int teacherID){
        String sql = "INSERT INTO files(file_path, file_name, file_type, teacher_id) VALUES(?, ?, ?, ?)";

        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,filePath);
            statement.setString(2,fileName);
            statement.setString(3,fileType);
            statement.setInt(4,teacherID);
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println("Problem in adding file to SQLite.");
        }
    }

    // This is for getting the file path.
    String getUserFilePath(int teacherID){
        String sql = "SELECT * FROM files WHERE teacher_id = ?";

        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,teacherID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getString("file_path");
        }
        catch (Exception e){
            System.out.println("Problem in getting file path.");
            return null;
        }
    }
}
