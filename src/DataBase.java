import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {

    DataBase(){}

    public Connection getConnect(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:school.db");
            connection.createStatement().execute("PRAGMA foreign_keys = ON;");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Couldn't connect","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return connection;
    }
}
