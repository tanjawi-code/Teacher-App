package com.example.teacherApp.dao;

import com.example.teacherApp.models.Settings;
import java.sql.*;

public class SettingsSQLite {

    private final DataBase dataBase;

    public SettingsSQLite(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    // This is for creating a table of settings.
    public void createSettingsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS settings ("+
                "id INTEGER PRIMARY KEY,"+
                "program_theme TEXT NOT NULL,"+
                "dashboard_color TEXT NOT NULL,"+
                "language TEXT NOT NULL,"+
                "json_recommendation BOOLEAN,"+
                "user_id INTEGER,"+
                "FOREIGN KEY (user_id) REFERENCES teachers(id) ON DELETE CASCADE"+
                ");";

        try (Connection connection = dataBase.getConnect(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
        catch (Exception e) {
            System.out.println("Problem at creating a table of settings");
        }
    }

    // This is for inserting user's configurations of settings.
    public void insertUserSettings(Settings settings) {
        String sql = "INSERT INTO settings(program_theme, dashboard_color, language, json_recommendation ,user_id) "+
                     "VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, settings.getProgramBackground());
            statement.setString(2, settings.getDashboardBackground());
            statement.setString(3, settings.getLanguage());
            statement.setBoolean(4, settings.isJsonRecommendation());
            statement.setInt(5, settings.getUsrId());
            statement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("A problem in adding user's settings");
        }
    }

    // This is for getting the user's configurations after opening the program.
    public Settings getUserSettings(int userId) {
        String sql = "SELECT * FROM settings WHERE user_id = ?";

        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String programBackgroundColor = resultSet.getString("program_theme");
                String dashboard = resultSet.getString("dashboard_color");
                String language = resultSet.getString("language");
                boolean jsonRecommendation = resultSet.getBoolean("json_recommendation");
                return new Settings(programBackgroundColor,dashboard,language,jsonRecommendation,userId);
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            System.out.println("Problem in selecting user's settings");
            return null;
        }
    }

    // This is for updating the backgrounds.
    public void changeBackgroundsColor(String programColor, String dashboardColor, int userId) {
        String sql = "UPDATE settings SET program_theme = ?, dashboard_color = ? WHERE user_id = ?";

        try (Connection connection = dataBase.getConnect(); PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, programColor);
            statement.setString(2, dashboardColor);
            statement.setInt(3, userId);
            statement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Problem in updating user's settings.");
        }
    }
}
