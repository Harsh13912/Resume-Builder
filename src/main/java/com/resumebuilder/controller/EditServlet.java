package com.resumebuilder.controller;

import com.resumebuilder.model.Resume;
import com.resumebuilder.util.XmlUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.File;
import com.resumebuilder.util.Config;

@WebServlet(name = "editServlet", value = "/editServlet")
public class EditServlet extends HttpServlet {

    // This servlet will handle GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get the filename from the URL parameter
        String fileName = request.getParameter("file");
        if (fileName == null || fileName.isEmpty() || fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            response.sendError(400, "Invalid filename."); // Bad Request
            return;
        }

        if (fileName == null || fileName.isEmpty()) {
            response.sendRedirect("dashboard.jsp"); // No file, go back to dashboard
            return;
        }

        // 2. Define the base path (Same as in ResumeServlet)
        String basePath = Config.RESUME_STORAGE_PATH;
        String fullPath = basePath + fileName;

        // 3. Load the Resume object from the XML file
        Resume resume = XmlUtil.loadResumeFromXml(fullPath);

        // 4. Set the loaded resume as an attribute on the request
        // This makes it available to the JSP
        request.setAttribute("resumeToEdit", resume);
        request.setAttribute("originalFileName", fileName); // <-- ADD THIS
        RequestDispatcher dispatcher = request.getRequestDispatcher("editResume.jsp");
        dispatcher.forward(request, response);
    }
}