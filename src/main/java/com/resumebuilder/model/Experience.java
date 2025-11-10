package com.resumebuilder.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Experience {
    private String jobTitle;
    private String company;
    private String jobYears;
    private String jobDescription;

    // JAXB no-arg constructor
    public Experience() {
    }

    public Experience(String jobTitle, String company, String jobYears, String jobDescription) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.jobYears = jobYears;
        this.jobDescription = jobDescription;
    }

    // Getters and Setters
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getJobYears() { return jobYears; }
    public void setJobYears(String jobYears) { this.jobYears = jobYears; }
    public String getJobDescription() { return jobDescription; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }
}