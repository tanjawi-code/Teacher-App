package com.example.teacherApp.models;

public class Settings {

    private final String programBackground;
    private final String dashboardBackground;
    private final String language;
    private final boolean jsonRecommendation;
    private final int usrId;

    public Settings(String programBackground, String dashboardBackground, String language, boolean jsonRecommendation, int usrId) {
        this.programBackground = programBackground;
        this.dashboardBackground = dashboardBackground;
        this.language = language;
        this.jsonRecommendation = jsonRecommendation;
        this.usrId = usrId;
    }

    public String getProgramBackground() {
        return programBackground;
    }
    public String getDashboardBackground() {
        return dashboardBackground;
    }
    public String getLanguage() {
        return language;
    }
    public boolean isJsonRecommendation() {
        return jsonRecommendation;
    }
    public int getUsrId() {
        return usrId;
    }
}
