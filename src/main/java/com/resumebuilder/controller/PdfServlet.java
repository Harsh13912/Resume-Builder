package com.resumebuilder.controller;

import com.resumebuilder.model.*; // Import all models
import com.resumebuilder.util.Config;
import com.resumebuilder.util.XmlUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// iText 7 Imports
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.File;
import java.io.IOException;
import java.util.StringJoiner; // <-- New Import for clean string joining

@WebServlet(name = "pdfServlet", value = "/pdfServlet")
public class PdfServlet extends HttpServlet {

    // Define colors for the Modern Template
    public static final Color MODERN_SIDEBAR_BG = new DeviceRgb(74, 92, 102);
    public static final Color MODERN_SIDEBAR_TEXT = ColorConstants.WHITE;
    public static final Color MODERN_HEADER_LINE = new DeviceRgb(74, 92, 102);
    public static final Color MODERN_GRAY_TEXT = new DeviceGray(0.5f);

    /**
     * A helper function to check if a string is valid (not null and not empty)
     */
    private boolean isStrValid(String str) {
        return str != null && !str.trim().isEmpty();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ... (Your existing doGet code from line 60 to 110 is identical) ...
        // 1. Get parameters
        String fileName = request.getParameter("file");
        String template = request.getParameter("template");

        // Security check
        if (fileName == null || fileName.isEmpty() || fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            response.sendError(400, "Invalid filename.");
            return;
        }
        if (template == null || template.isEmpty()) {
            template = "classic"; // Default
        }

        // 2. Load the Resume object
        String basePath = Config.RESUME_STORAGE_PATH;
        String fullPath = basePath + fileName;
        Resume resume = XmlUtil.loadResumeFromXml(fullPath);

        if (resume == null) {
            response.sendError(500, "Could not load resume data.");
            return;
        }

        // 3. Set up the HTTP response
        response.setContentType("application/pdf");

        // FIX: Create a dynamic filename
        String templateName = template.substring(0, 1).toUpperCase() + template.substring(1);
        String pdfFileName = resume.getName() + "_" + templateName + "Resume.pdf";
        response.setHeader("Content-Disposition", "inline; filename=\"" + pdfFileName + "\"");

        // 4. Create the PDF document
        try (PdfWriter writer = new PdfWriter(response.getOutputStream());
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // FIX: Set the PDF's internal "Title" property
            String pdfTitle = resume.getName() + " - " + templateName + " Resume";
            pdf.getDocumentInfo().setTitle(pdfTitle);

            // Set margins to 0 for the modern template, it handles its own padding
            if (template.equals("modern")) {
                document.setMargins(0, 0, 0, 0);
            } else {
                document.setMargins(36, 36, 36, 36); // Default for classic
            }

            // 5. CHOOSE THE TEMPLATE
            switch (template) {
                case "modern":
                    drawModernTemplate(document, resume);
                    break;
                case "classic":
                default:
                    drawClassicTemplate(document, resume);
                    break;
            }

            System.out.println("iText PDF generated successfully with template: " + template);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error generating PDF with iText.");
        }
    }

    /**
     * TEMPLATE 1: "Classic" template with all bug fixes.
     */
    private void drawClassicTemplate(Document document, Resume resume) throws IOException {
        document.setMargins(36, 36, 36, 36);
        PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        // --- Personal Info (Assumed required) ---
        document.add(new Paragraph(resume.getName())
                .setFont(fontBold).setFontSize(20).setTextAlignment(TextAlignment.CENTER));
        String contactInfo = resume.getEmail() + " | " + resume.getContact() + " | " + resume.getLinkedin();
        document.add(new Paragraph(contactInfo)
                .setFont(fontRegular).setFontSize(11).setTextAlignment(TextAlignment.CENTER));

        // --- Summary ---
        if (isStrValid(resume.getSummary())) {
            document.add(new Paragraph("Summary")
                    .setFont(fontBold).setFontSize(14).setMarginTop(15));
            document.add(new Paragraph(resume.getSummary())
                    .setFont(fontRegular).setFontSize(11).setMarginBottom(5));
        }

        // --- Education ---
        if (resume.getEducationList() != null && !resume.getEducationList().isEmpty()) {
            document.add(new Paragraph("Education")
                    .setFont(fontBold).setFontSize(14).setMarginTop(15));
            for (Education edu : resume.getEducationList()) {
                Paragraph p = new Paragraph()
                        .add(new Text(edu.getDegree() + " - " + edu.getSchool()))
                        .setFont(fontRegular).setFontSize(11).setMarginBottom(5);
                if (isStrValid(edu.getGradYear())) {
                    p.add("\n").add("Year: " + edu.getGradYear());
                }
                if (isStrValid(edu.getScore())) {
                    p.add("\n").add("Score: " + edu.getScore());
                }
                document.add(p);
            }
        }

        // --- Experience ---
        if (resume.getExperienceList() != null && !resume.getExperienceList().isEmpty()) {
            document.add(new Paragraph("Experience")
                    .setFont(fontBold).setFontSize(14).setMarginTop(15));
            for (Experience exp : resume.getExperienceList()) {
                document.add(new Paragraph()
                        .add(new Text(exp.getJobTitle() + " at " + exp.getCompany()))
                        .add("\n").add("Years: " + exp.getJobYears())
                        .add("\n").add(exp.getJobDescription())
                        .setFont(fontRegular).setFontSize(11).setMarginBottom(5));
            }
        }

        // --- Projects ---
        if (resume.getProjectList() != null && !resume.getProjectList().isEmpty()) {
            document.add(new Paragraph("Projects")
                    .setFont(fontBold).setFontSize(14).setMarginTop(15));
            for (Project proj : resume.getProjectList()) {
                document.add(new Paragraph()
                        .add(new Text(proj.getTitle()))
                        .add("\n").add(proj.getDescription())
                        .setFont(fontRegular).setFontSize(11).setMarginBottom(5));
            }
        }

        // --- Certifications ---
        if (resume.getCertificationList() != null && !resume.getCertificationList().isEmpty()) {
            document.add(new Paragraph("Certifications & Awards")
                    .setFont(fontBold).setFontSize(14).setMarginTop(15));
            for (Certification cert : resume.getCertificationList()) {
                document.add(new Paragraph("• " + cert.getTitle())
                        .setFont(fontRegular).setFontSize(11).setMarginBottom(3));
            }
        }

        // --- Achievements ---
        if (resume.getAchievementList() != null && !resume.getAchievementList().isEmpty()) {
            document.add(new Paragraph("Academic Achievements")
                    .setFont(fontBold).setFontSize(14).setMarginTop(15));
            for (Achievement ach : resume.getAchievementList()) {
                document.add(new Paragraph("• " + ach.getDescription())
                        .setFont(fontRegular).setFontSize(11).setMarginBottom(3));
            }
        }

        // --- Skills ---
        if (isStrValid(resume.getSkills())) {
            document.add(new Paragraph("Skills")
                    .setFont(fontBold).setFontSize(14).setMarginTop(15));
            document.add(new Paragraph(resume.getSkills())
                    .setFont(fontRegular).setFontSize(11));
        }
    }

    /**
     * TEMPLATE 2: "Modern" template with all bug fixes.
     */
    private void drawModernTemplate(Document document, Resume resume) throws IOException {
        // Define Fonts
        PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        // --- 1. Main Layout Table ---
        PageSize pageSize = document.getPdfDocument().getDefaultPageSize();
        Table table = new Table(UnitValue.createPercentArray(new float[]{35f, 65f}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setHeight(pageSize.getHeight());

        // --- 2. LEFT COLUMN (Sidebar) ---
        Cell sidebar = new Cell();
        sidebar.setBackgroundColor(MODERN_SIDEBAR_BG);
        sidebar.setFontColor(MODERN_SIDEBAR_TEXT);
        sidebar.setBorder(Border.NO_BORDER);
        sidebar.setVerticalAlignment(VerticalAlignment.TOP);
        sidebar.setPaddingTop(40);
        sidebar.setPaddingRight(25);
        sidebar.setPaddingBottom(25);
        sidebar.setPaddingLeft(25);

        // --- 3. RIGHT COLUMN (Main Content) ---
        Cell mainContent = new Cell();
        mainContent.setPadding(30);
        mainContent.setBorder(Border.NO_BORDER);
        mainContent.setVerticalAlignment(VerticalAlignment.TOP);

        // --- 4. Populate Sidebar (Left Column) ---

        // Add "PROFILE"
        if (isStrValid(resume.getSummary())) {
            sidebar.add(createSidebarHeader("PROFILE", fontBold));
            sidebar.add(new Paragraph(resume.getSummary())
                    .setFont(fontRegular).setFontSize(10).setMarginBottom(20));
        }

        // Add "CONTACT"
        sidebar.add(createSidebarHeader("CONTACT", fontBold));
        if (isStrValid(resume.getEmail())) {
            sidebar.add(new Paragraph(resume.getEmail()).setFont(fontRegular).setFontSize(10));
        }
        if (isStrValid(resume.getContact())) {
            sidebar.add(new Paragraph(resume.getContact()).setFont(fontRegular).setFontSize(10));
        }
        if (isStrValid(resume.getLinkedin())) {
            sidebar.add(new Paragraph(resume.getLinkedin()).setFont(fontRegular).setFontSize(10).setMarginBottom(20));
        }

        // Add "SKILLS"
        if (isStrValid(resume.getSkills())) {
            sidebar.add(createSidebarHeader("SKILLS", fontBold));
            String[] skills = resume.getSkills().split(",");
            for (String skill : skills) {
                sidebar.add(new Paragraph("• " + skill.trim())
                        .setFont(fontRegular).setFontSize(10));
            }
        }

        // --- 5. Populate Main Content (Right Column) ---

        // Add Name (Required)
        mainContent.add(new Paragraph(resume.getName())
                .setFont(fontBold).setFontSize(36).setMarginBottom(20)); // Added margin

        // Add "EDUCATION"
        if (resume.getEducationList() != null && !resume.getEducationList().isEmpty()) {
            mainContent.add(createMainHeader("EDUCATION", fontBold));
            for (Education edu : resume.getEducationList()) {
                mainContent.add(new Paragraph(edu.getDegree())
                        .setFont(fontRegular).setFontSize(11));
                mainContent.add(new Paragraph(edu.getSchool())
                        .setFont(fontRegular).setFontSize(11).setFontColor(MODERN_GRAY_TEXT));

                // Use StringJoiner for clean "Year | Score" line
                StringJoiner sj = new StringJoiner(" | ");
                if (isStrValid(edu.getGradYear())) {
                    sj.add("Year: " + edu.getGradYear());
                }
                if (isStrValid(edu.getScore())) {
                    sj.add("Score: " + edu.getScore());
                }
                // Only add the line if it has content
                if (sj.length() > 0) {
                    mainContent.add(new Paragraph(sj.toString())
                            .setFont(fontRegular).setFontSize(11).setFontColor(MODERN_GRAY_TEXT)
                            .setMarginBottom(15));
                }
            }
        }

        // Add "EXPERIENCE"
        if (resume.getExperienceList() != null && !resume.getExperienceList().isEmpty()) {
            mainContent.add(createMainHeader("EXPERIENCE", fontBold));
            for (Experience exp : resume.getExperienceList()) {
                mainContent.add(new Paragraph(exp.getJobTitle())
                        .setFont(fontRegular).setFontSize(11));
                mainContent.add(new Paragraph(exp.getCompany() + " | " + exp.getJobYears())
                        .setFont(fontRegular).setFontSize(11).setFontColor(MODERN_GRAY_TEXT));
                mainContent.add(new Paragraph(exp.getJobDescription())
                        .setFont(fontRegular).setFontSize(11).setMarginBottom(15));
            }
        }

        // Add "PROJECTS"
        if (resume.getProjectList() != null && !resume.getProjectList().isEmpty()) {
            mainContent.add(createMainHeader("PROJECTS", fontBold));
            for (Project proj : resume.getProjectList()) {
                mainContent.add(new Paragraph(proj.getTitle())
                        .setFont(fontRegular).setFontSize(11));
                mainContent.add(new Paragraph(proj.getDescription())
                        .setFont(fontRegular).setFontSize(11).setMarginBottom(15));
            }
        }

        // Add "CERTIFICATIONS"
        if (resume.getCertificationList() != null && !resume.getCertificationList().isEmpty()) {
            mainContent.add(createMainHeader("CERTIFICATIONS & AWARDS", fontBold));
            for (Certification cert : resume.getCertificationList()) {
                mainContent.add(new Paragraph("• " + cert.getTitle())
                        .setFont(fontRegular).setFontSize(11).setMarginBottom(5));
            }
        }

        // Add "ACHIEVEMENTS"
        if (resume.getAchievementList() != null && !resume.getAchievementList().isEmpty()) {
            mainContent.add(createMainHeader("ACADEMIC ACHIEVEMENTS", fontBold));
            for (Achievement ach : resume.getAchievementList()) {
                mainContent.add(new Paragraph("• " + ach.getDescription())
                        .setFont(fontRegular).setFontSize(11).setMarginBottom(5));
            }
        }

        // --- 6. Add Cells to Table ---
        table.addCell(sidebar);
        table.addCell(mainContent);

        // --- 7. Add Table to Document ---
        document.add(table);
    }

    /** Helper for Sidebar headers */
    private Paragraph createSidebarHeader(String text, PdfFont font) {
        return new Paragraph(text)
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(5);
    }

    /** Helper for Main Content headers (with underline) */
    private Paragraph createMainHeader(String text, PdfFont font) {
        return new Paragraph(text)
                .setFont(font)
                .setFontSize(14)
                .setBorderBottom(new SolidBorder(MODERN_HEADER_LINE, 1f))
                .setMarginBottom(10)
                .setMarginTop(15);
    }
}