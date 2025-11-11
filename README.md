# 📄 Java Resume Builder

A full-stack dynamic web application built using **Java, Servlets, and JSP** that allows users to create, store, edit, and export professional resumes in multiple formats (PDF and XML). This project follows a classic **Model-View-Controller (MVC)** architecture.

## 🚀 Live Deployment

This project is successfully deployed and live\!

  * **Live Link (Render):** **[https://resumebuilderproject.onrender.com/](https://resumebuilderproject.onrender.com/)**

-----

## 📸 Features

✅ **Dynamic Form Builder** - Add or remove multiple sections for Education, Experience, Projects, etc.  
✅ **XML Data Storage** - Uses **JAXB** to save and load all resume data as structured XML files  
✅ **Dual PDF Template Generation** - Generates a PDF in two different styles ("Classic" and "Modern") using the **iText** library  
✅ **Edit Existing Resumes** - Load any saved XML resume back into the form for editing and overwriting  
✅ **Student Dashboard** - A central hub to view, edit, and generate PDFs for all saved resumes, sorted by newest first  
✅ **MVC Architecture** - Clean separation of concerns with Models (Java Beans), Views (JSP), and Controllers (Servlets)  
✅ **Polished UI/UX** - A modern, responsive CSS theme for both the form and the dashboard

---

## 🗃️ Tech Stack

### Backend

- **Java** - JDK 17
- **Servlet Container** - Apache Tomcat 10.1
- **Servlets** - Jakarta Servlet 6.0
- **Views** - JavaServer Pages (JSP) with JSTL 3.0
- **XML Binding** - JAXB 4.0 (for XML save/load)
- **PDF Generation** - iText 7 (layout module)

### Frontend

- **HTML5** & **CSS3**
- **JavaScript** - For dynamic form sections

### Build System

- **Apache Maven**

---

## 🚀 Local Development Setup

### Prerequisites

```bash
JDK >= 17.0.0
Maven >= 3.8.0
Tomcat >= 10.1
```

### 1. Clone Repository

```bash
git clone <your-repo-url>
cd ResumeBuilder
```

### 2. Configure Storage Path

This project saves XML files to a folder on your local machine. You **must** configure the storage location.

1. Create a folder on your computer (e.g., `D:/MyProjects/ResumeBuilderData/resumes`)
2. Open the file: `src/main/java/com/resumebuilder/util/Config.java`
3. Update the `RESUME_STORAGE_PATH` variable:

```java
// Use forward slashes, even on Windows
public static final String RESUME_STORAGE_PATH = "D:/MyProjects/ResumeBuilderData/resumes/";
```

4. The `user_data` folder in this project is for example only; the live path is set in `Config.java`

### 3. Build Project

```bash
mvn clean package
```

This will create a `target/ResumeBuilder.war` file.

### 4. Configure Tomcat Server in IntelliJ

1. Install the **"Smart Tomcat"** plugin in IntelliJ (Settings > Plugins)
2. Go to **Run > Edit Configurations...**
3. Click `+` and select **Smart Tomcat**
4. **Name:** `Tomcat 10.1`
5. **Tomcat Server:** Point it to the folder where you unzipped Apache Tomcat
6. **Deployment Directory:** Point it to the `target/ResumeBuilder` folder
7. **Context Path:** Set this to `/ResumeBuilder`
8. Click **OK**

### 5. Access Application

- **Dashboard:** http://localhost:8080/ResumeBuilder/dashboard.jsp
- **Editor:** http://localhost:8080/ResumeBuilder/editResume.jsp

---

## 📁 Project Structure

```
ResumeBuilder/
│
├── src/main/
│   ├── java/com/resumebuilder/
│   │   ├── controller/
│   │   │   ├── EditServlet.java       # Loads resume data for editing
│   │   │   ├── PdfServlet.java        # Generates the PDF
│   │   │   └── ResumeServlet.java     # Saves new/updated resumes
│   │   │
│   │   ├── model/
│   │   │   ├── Resume.java            # Main resume object (root XML element)
│   │   │   ├── Education.java         # Education sub-object
│   │   │   ├── Experience.java        # Experience sub-object
│   │   │   ├── Project.java           # Project sub-object
│   │   │   ├── Certification.java     # Certification sub-object
│   │   │   └── Achievement.java       # Achievement sub-object
│   │   │
│   │   └── util/
│   │       ├── Config.java            # ⭐⭐⭐ Configures the XML save path ⭐⭐⭐
│   │       └── XmlUtil.java           # Handles all JAXB (XML) logic
│   │
│   └── webapp/
│       ├── css/
│       │   └── style.css              # Main stylesheet
│       ├── WEB-INF/
│       │   └── web.xml                # Web app configuration (Jakarta EE 10)
│       │
│       ├── dashboard.jsp              # (View) Lists all saved resumes
│       ├── editResume.jsp             # (View) The main form for creating/editing
│       └── index.jsp                  # (Not used, redirects to dashboard)
│
├── user_data/
│   └── resumes/
│       └── (Example saved XML files)
│
└── pom.xml                            # Maven dependencies and project config
```

---

## 📌 API Endpoints

### Servlet Mappings

| Method | Endpoint | Servlet | Description |
|--------|----------|---------|-------------|
| POST | `/resumeServlet` | `ResumeServlet` | Saves a new or existing resume from the form |
| GET | `/editServlet` | `EditServlet` | Takes a `?file=` parameter, loads XML and forwards to view |
| GET | `/pdfServlet` | `PdfServlet` | Takes `?file=` and `?template=` parameters, generates PDF |
| GET | `/hello` | `HelloServlet` | Simple test servlet to check if Tomcat is running |

---

## 🧪 Testing

### Test User Flow

1. Run the application and open http://localhost:8080/ResumeBuilder/dashboard.jsp
2. Click the **"+ Create New Resume"** button
3. Fill out the **Personal Information** (all fields are required)
4. Click **"+ Add Education"** twice and fill in two education entries
5. Click **"+ Add Project"** and fill in one project
6. Leave the **Experience** section empty
7. Click **"Save & Generate Resume"**
8. You will be redirected to the dashboard with your new resume at the top
9. Click **"View Modern"** to open the PDF (empty sections are hidden)
10. Click **"View Classic"** to see the classic template
11. Click **"Edit"** to load the form with pre-filled data
12. Remove one education section using the **"Remove"** button
13. Click **"Save & Generate Resume"** to overwrite the existing file
14. View the PDF again to verify changes

---

## 🛠 Troubleshooting

### "HTTP Status 400 - Bad Request: Invalid filename"

**Solution:** This is a security check working correctly. Clear your browser cache with a hard reload (`Ctrl+Shift+R` or `Ctrl+F5`) on the dashboard page.

### "HTTP Status 404 - Not Found" for `.jsp` files

**Solution:** Run `mvn clean package` and ensure your Smart Tomcat configuration points to the `target/ResumeBuilder` directory.

### `java.io.FileNotFoundException` in console

**Solution:** Double-check the path in `Config.java`. Ensure it's an absolute path using forward slashes `/` and ends with a final `/`.

---

## 🎓 Learning Resources

- [Java Servlets Tutorial](https://docs.oracle.com/javaee/7/tutorial/servlets.htm)
- [JAXB Documentation](https://eclipse-ee4j.github.io/jaxb-ri/)
- [iText 7 Documentation](https://itextpdf.com/en/resources/books/itext-7-jump-start-tutorial-java)
- [Apache Tomcat 10 Docs](https://tomcat.apache.org/tomcat-10.1-doc/)

---

## 👨‍💻 Author

**Harsh Kumar**

- GitHub: [@Harsh13912](https://github.com/Harsh13912)
- Email: 23bcs13912@gmail.com

---


**⭐ Star this repo if you found it helpful!**

