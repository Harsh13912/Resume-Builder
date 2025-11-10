<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="com.resumebuilder.util.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Resume Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .resume-list {
            list-style: none; padding: 0;
        }
        .resume-list li {
            background: #fdfdfd; border: 1px solid #eee; padding: 15px;
            margin-bottom: 10px; border-radius: 5px; display: flex;
            justify-content: space-between; align-items: center;
        }
        .resume-list a {
            text-decoration: none; font-weight: bold;
        }
        .btn {
            padding: 8px 12px;
            color: #333; /* Dark text */
            text-decoration: none; border-radius: 5px; font-size: 0.9em;
            background-color: #e0e0e0; /* Default gray background */
            border: 1px solid #ccc;
            margin-left: 5px;
        }
        .btn-edit {
            background-color: #2ecc71; /* Green */
            border-color: #2ecc71;
            color: white;
        }
        .btn-classic {
            background-color: #3498db; /* Blue */
            border-color: #3498db;
            color: white;
        }
        .btn-modern {
            background-color: #9b59b6; /* Purple */
            border-color: #9b59b6;
            color: white;
        }
    </style>
</head>
<body>

    <div style="max-width: 800px; margin: 20px auto; padding: 30px; background: #fff; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.05);">
        <h1>My Resumes</h1>

        <a href="editResume.jsp" class="btn" style="margin-bottom: 20px; display: inline-block; background-color: #3498db; color: white;">
            + Create New Resume
        </a>

        <ul class="resume-list">
            <%
                            String resumesPath = Config.RESUME_STORAGE_PATH;
                            File resumesDir = new File(resumesPath);
                            File[] resumeFiles = resumesDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

                            if (resumeFiles != null && resumeFiles.length > 0) {
                                // --- FIX: Sort files by last modified date, newest first ---
                                java.util.Arrays.sort(resumeFiles, java.util.Comparator.comparingLong(File::lastModified).reversed());
                    for (File file : resumeFiles) {
            %>
                        <li>
                            <span><%= file.getName() %></span>
                            <div style="display: flex; align-items: center;">

                                <a href="editServlet?file=<%= file.getName() %>"
                                   class="btn btn-edit">Edit</a>

                                <a href="pdfServlet?file=<%= file.getName() %>&template=classic"
                                   class="btn btn-classic" target="_blank">View Classic</a>

                                <a href="pdfServlet?file=<%= file.getName() %>&template=modern"
                                   class="btn btn-modern" target="_blank">View Modern</a>

                            </div>
                        </li>
            <%
                    } // Closes for
                } else {
            %>
                    <li>No resumes found.</li>
            <%
                } // Closes if/else
            %>
        </ul>
    </div>
    </body>
</html>