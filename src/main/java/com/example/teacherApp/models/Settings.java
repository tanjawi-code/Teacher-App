package com.example.teacherApp.models;

public class Settings {

    private String programBackground;
    private String dashboardBackground;
    private final String language;
    private boolean jsonRecommendation;
    private final int usrId;

    public Settings(String programBackground, String dashboardBackground, String language, boolean jsonRecommendation, int usrId) {
        this.programBackground = programBackground;
        this.dashboardBackground = dashboardBackground;
        this.language = language;
        this.jsonRecommendation = jsonRecommendation;
        this.usrId = usrId;
    }

    // Getters.
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

    // Setters
    public void setProgramBackground(String programBackground) {
        this.programBackground = programBackground;
    }
    public void setDashboardBackground(String dashboardBackground) {
        this.dashboardBackground = dashboardBackground;
    }
    public void setJsonRecommendation(boolean jsonRecommendation) {
        this.jsonRecommendation = jsonRecommendation;
    }
}
