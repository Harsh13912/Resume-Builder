package com.resumebuilder.controller;

import com.resumebuilder.util.Config;
import com.resumebuilder.util.XmlUtil;
import com.resumebuilder.model.Education;
import com.resumebuilder.model.Experience;
import com.resumebuilder.model.Project;
import com.resumebuilder.model.Certification;
import com.resumebuilder.model.Achievement;
import com.resumebuilder.model.Resume;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebServlet(name = "resumeServlet", value = "/resumeServlet")
public class ResumeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Create the resume object
        Resume resume = new Resume();

        // --- Personal Info (Same as before) ---
        resume.setName(request.getParameter("name"));
        resume.setEmail(request.getParameter("email"));
        resume.setContact(request.getParameter("contact"));
        resume.setLinkedin(request.getParameter("linkedin"));
        resume.setSummary(request.getParameter("summary"));

        // --- Education (NEW LOGIC) ---
        // Get arrays of all the education fields
        String[] degrees = request.getParameterValues("degree");
        String[] schools = request.getParameterValues("school");
        String[] gradYears = request.getParameterValues("gradYear");
        String[] scores = request.getParameterValues("score"); // <-- ADD THIS

        // Loop through the education entries (if any exist)
        if (degrees != null) {
            for (int i = 0; i < degrees.length; i++) {
                // Check for blank entries
                if (degrees[i] != null && !degrees[i].trim().isEmpty()) {
                    // Update the constructor call
                    Education edu = new Education(degrees[i], schools[i], gradYears[i], scores[i]); // <-- UPDATE THIS
                    resume.addEducation(edu);
                }
            }
        }

        // --- Experience (NEW LOGIC) ---
        // Get arrays of all the experience fields
        String[] jobTitles = request.getParameterValues("jobTitle");
        String[] companies = request.getParameterValues("company");
        String[] jobYearsList = request.getParameterValues("jobYears");
        String[] jobDescriptions = request.getParameterValues("jobDescription");

        // Loop through the experience entries
        if (jobTitles != null) {
            for (int i = 0; i < jobTitles.length; i++) {
                if (jobTitles[i] != null && !jobTitles[i].trim().isEmpty()) {
                    Experience exp = new Experience(jobTitles[i], companies[i], jobYearsList[i], jobDescriptions[i]);
                    resume.addExperience(exp);
                }
            }
        }

        // --- Skills ---
        resume.setSkills(request.getParameter("skills"));

        // --- CERTIFICATIONS (NEW) ---
        String[] certifications = request.getParameterValues("certification");
        if (certifications != null) {
            for (String certTitle : certifications) {
                if (certTitle != null && !certTitle.trim().isEmpty()) {
                    resume.addCertification(new Certification(certTitle));
                }
            }
        }

        // --- ACHIEVEMENTS (NEW) ---
        String[] achievements = request.getParameterValues("achievement");
        if (achievements != null) {
            for (String achDesc : achievements) {
                if (achDesc != null && !achDesc.trim().isEmpty()) {
                    resume.addAchievement(new Achievement(achDesc));
                }
            }
        }

        // --- PROJECTS (NEW) ---
        String[] projectTitles = request.getParameterValues("projectTitle");
        String[] projectDescriptions = request.getParameterValues("projectDescription");

        if (projectTitles != null) {
            for (int i = 0; i < projectTitles.length; i++) {
                if (projectTitles[i] != null && !projectTitles[i].trim().isEmpty()) {
                    resume.addProject(new Project(projectTitles[i], projectDescriptions[i]));
                }
            }
        }

        // 2. TEST: Print the received data (check console)
        System.out.println("--- NEW RESUME DATA RECEIVED ---");
        System.out.println("Name: " + resume.getName());
        System.out.println("Email: " + resume.getEmail());
        System.out.println("Education entries: " + resume.getEducationList().size());
        System.out.println("Experience entries: " + resume.getExperienceList().size());
        System.out.println("---------------------------------");

        // 3. Save this 'resume' object to an XML file.
        String basePath = Config.RESUME_STORAGE_PATH;
        String originalFileName = request.getParameter("originalFileName");
        String fileName;

        if (originalFileName != null && !originalFileName.isEmpty()) {
            // We are EDITING, so overwrite the original file
            fileName = originalFileName;
        } else {
            // We are CREATING a new file
            String safeName = (resume.getName() != null && !resume.getName().isEmpty()) ? resume.getName() : "Unnamed_Resume";
            safeName = safeName.replaceAll("[^a-zA-Z0-9_]", "_"); // Sanitize
            fileName = safeName + "_" + System.currentTimeMillis() + ".xml";
        }

        String fullPath = basePath + fileName;

        // Call our utility to save the file
        XmlUtil.saveResumeAsXml(resume, fullPath);

        // 4. Redirect the user back to the dashboard
        response.sendRedirect("dashboard.jsp");
    }
}