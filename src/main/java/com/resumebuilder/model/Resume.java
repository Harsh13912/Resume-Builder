package com.resumebuilder.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

// This annotation tells JAXB that this class is the "root" of the XML file
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume {

    // Personal Info
    private String name;
    private String email;
    private String contact;
    private String linkedin;
    private String summary;

    @XmlElement(name = "education")
    private List<Education> educationList = new ArrayList<>();

    @XmlElement(name = "experience")
    private List<Experience> experienceList = new ArrayList<>();

    private String skills; // Storing as a single string for now

    // --- THESE WERE MISSING ---
    @XmlElement(name = "certification")
    private List<Certification> certificationList = new ArrayList<>();

    @XmlElement(name = "achievement")
    private List<Achievement> achievementList = new ArrayList<>();

    // ... (after achievementList) ...
    @XmlElement(name = "project")
    private List<Project> projectList = new ArrayList<>();
    // -------------------------

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getLinkedin() { return linkedin; }
    public void setLinkedin(String linkedin) { this.linkedin = linkedin; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public List<Education> getEducationList() { return educationList; }
    public void setEducationList(List<Education> educationList) { this.educationList = educationList; }
    public List<Experience> getExperienceList() { return experienceList; }
    public void setExperienceList(List<Experience> experienceList) { this.experienceList = experienceList; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    // --- THESE WERE MISSING ---
    public List<Certification> getCertificationList() { return certificationList; }
    public void setCertificationList(List<Certification> certificationList) { this.certificationList = certificationList; }

    public List<Achievement> getAchievementList() { return achievementList; }
    public void setAchievementList(List<Achievement> achievementList) { this.achievementList = achievementList; }

    // ... (after get/setAchievementList) ...
    public List<Project> getProjectList() { return projectList; }
    public void setProjectList(List<Project> projectList) { this.projectList = projectList; }
    // -------------------------


    // Helper methods to add items
    public void addEducation(Education edu) {
        this.educationList.add(edu);
    }
    public void addExperience(Experience exp) {
        this.experienceList.add(exp);
    }

    // --- THESE WERE MISSING ---
    public void addCertification(Certification cert) {
        this.certificationList.add(cert);
    }

    public void addAchievement(Achievement ach) {
        this.achievementList.add(ach);
    }

    // ... (after addAchievement) ...
    public void addProject(Project proj) {
        this.projectList.add(proj);
    }
    // -------------------------
}