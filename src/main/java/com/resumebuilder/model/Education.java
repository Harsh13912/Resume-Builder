package com.resumebuilder.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Education {
    private String degree;
    private String school;
    private String gradYear;
    private String score; // <-- ADD THIS

    // JAXB needs a no-argument constructor
    public Education() {
    }

    // Update the constructor
    public Education(String degree, String school, String gradYear, String score) {
        this.degree = degree;
        this.school = school;
        this.gradYear = gradYear;
        this.score = score; // <-- ADD THIS
    }

    // Getters and Setters
    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    public String getGradYear() { return gradYear; }
    public void setGradYear(String gradYear) { this.gradYear = gradYear; }

    // <-- ADD GETTER AND SETTER FOR SCORE -->
    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }
}