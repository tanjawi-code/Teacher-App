package com.example.teacherApp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;

@JsonPropertyOrder({"subject","totalStudents","passedStudents","failedStudents","males","females",
                   "averagePoint","averageAge","highest_point","lowest_point","succussRate","cities"})
public class ClassStatistics {

    private String subject;
    private int totalStudents;
    private int passedStudents;
    private int failedStudents;
    private int males;
    private int females;
    private String averagePoint;
    private String averageAge;
    @JsonProperty("highest_point")
    private double highPoint;
    @JsonProperty("lowest_point")
    private double lowPoint;
    private String succussRate;
    HashMap<String, Integer> cities;

    public ClassStatistics(){}

    public ClassStatistics(String subject, int totalStudents, int passedStudents, int failedStudents, int males,
                           int females, String averagePoint, String averageAge, double highPoint, double lowPoint,
                           String succussRate, HashMap<String, Integer> cities) {
        this.subject = subject;
        this.totalStudents = totalStudents;
        this.passedStudents = passedStudents;
        this.failedStudents = failedStudents;
        this.males = males;
        this.females = females;
        this.averagePoint = averagePoint;
        this.averageAge = averageAge;
        this.highPoint = highPoint;
        this.lowPoint = lowPoint;
        this.succussRate = succussRate;
        this.cities = cities;
    }

    public String getSubject() {
        return subject;
    }
    public int getTotalStudents() {
        return totalStudents;
    }
    public int getPassedStudents() {
        return passedStudents;
    }
    public int getFailedStudents() {
        return failedStudents;
    }
    public int getMales() {
        return males;
    }
    public int getFemales() {
        return females;
    }
    public String getAveragePoint() {
        return averagePoint;
    }
    public String getAverageAge() {
        return averageAge;
    }
    public double getHighPoint() {
        return highPoint;
    }
    public double getLowPoint() {
        return lowPoint;
    }
    public String getSuccussRate() {
        return succussRate;
    }
    public HashMap<String, Integer> getCities() {
        return cities;
    }
}
