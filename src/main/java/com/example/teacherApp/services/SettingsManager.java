package com.example.teacherApp.services;

import com.example.teacherApp.dao.SettingsSQLite;
import com.example.teacherApp.models.Settings;

public class SettingsManager {

    private final SettingsSQLite settingsSQLite;

    public SettingsManager(SettingsSQLite settingsSQLite) {
        this.settingsSQLite = settingsSQLite;
    }

    // This is for saving default settings of the user.
    public void userDefaultSettings(Settings settings) {
        settingsSQLite.insertUserSettings(settings);
    }

    // This is for getting the user's settings after opening the program.
    public Settings getUserSettings(int userId) {
        return settingsSQLite.getUserSettings(userId);
    }

    // This is for changing the background color (might be the whole program or the dashboard).
    public void changeUserBackgroundColor(String programColor, String dashboardColor, int userId) {
        settingsSQLite.changeBackgroundsColor(programColor, dashboardColor, userId);
    }

    // This is for hiding json recommendation.
    public void stopJsonRecommendation(boolean jsonRecommendation, int userid) {
        settingsSQLite.hideJsonRecommendation(jsonRecommendation, userid);
    }
}
