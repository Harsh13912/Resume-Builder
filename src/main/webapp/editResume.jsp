<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${isEditing ? 'Edit Resume' : 'Create New Resume'}</title>
    <link rel="stylesheet" href="css/style.css">
    </head>
<body>

    <h1>Resume Builder</h1>

    <c:set var="resume" value="${resumeToEdit}" />
    <c:set var="isEditing" value="${not empty resume}" />

    <p>${isEditing ? "Editing Resume" : "Create a New Resume"}</p>

    <form action="resumeServlet" method="post">

        <input type="hidden" name="originalFileName" value="${originalFileName}">

        <h2>Personal Information</h2>
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${resume.name}" required />
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${resume.email}" required />
        </div>
        <div class="form-group">
            <label for="contact">Contact:</label>
            <input type="text" id="contact" name="contact" value="${resume.contact}" required />
        </div>
        <div class="form-group">
            <label for="linkedin">LinkedIn:</label>
            <input type="text" id="linkedin" name="linkedin" value="${resume.linkedin}" />
        </div>
        <div class="form-group">
            <label for="summary">Summary:</label>
            <textarea id="summary" name="summary" required>${resume.summary}</textarea>
        </div>

        <hr/>

        <h2>Education</h2>
        <div id="educationContainer">
            <c:forEach var="edu" items="${resume.educationList}">
                <div class="dynamic-section">
                    <div class="form-group">
                        <label>Qualification:</label>
                        <input type="text" name="degree" value="${edu.degree}" />
                    </div>
                    <div class="form-group">
                        <label>Institution:</label>
                        <input type="text" name="school" value="${edu.school}" />
                    </div>
                    <div class="form-group">
                        <label>Year:</label>
                        <input type="text" name="gradYear" value="${edu.gradYear}" />
                    </div>
                    <div class="form-group">
                        <label>Score:</label>
                        <input type="text" name="score" value="${edu.score}" />
                    </div>
                    <button type="button" class="btn-remove" onclick="removeElement(this)">Remove</button>
                </div>
            </c:forEach>
        </div>
        <button type="button" onclick="addSection('educationContainer', 'eduTemplate')">
            + Add Education
        </button>

        <hr/>
        <h2>Experience</h2>
        <div id="experienceContainer">
            <c:forEach var="exp" items="${resume.experienceList}">
                <div class="dynamic-section">
                    <div class="form-group">
                        <label>Job Title:</label>
                        <input type="text" name="jobTitle" value="${exp.jobTitle}" />
                    </div>
                    <div class="form-group">
                        <label>Company:</label>
                        <input type="text" name="company" value="${exp.company}" />
                    </div>
                    <div class="form-group">
                        <label>Years:</label>
                        <input type="text" name="jobYears" value="${exp.jobYears}" />
                    </div>
                    <div class="form-group">
                        <label>Description:</label>
                        <textarea name="jobDescription">${exp.jobDescription}</textarea>
                    </div>
                    <button type="button" class="btn-remove" onclick="removeElement(this)">Remove</button>
                </div>
            </c:forEach>
        </div>
        <button type="button" onclick="addSection('experienceContainer', 'expTemplate')">
            + Add Experience
        </button>

        <hr/>
        <h2>Projects</h2>
        <div id="projectContainer">
            <c:forEach var="proj" items="${resume.projectList}">
                <div class="dynamic-section">
                    <div class="form-group">
                        <label>Project Title:</label>
                        <input type="text" name="projectTitle" value="${proj.title}" />
                    </div>
                    <div class="form-group">
                        <label>Project Description:</label>
                        <textarea name="projectDescription">${proj.description}</textarea>
                    </div>
                    <button type="button" class="btn-remove" onclick="removeElement(this)">Remove</button>
                </div>
            </c:forEach>
        </div>
        <button type="button" onclick="addSection('projectContainer', 'projTemplate')">
            + Add Project
        </button>

        <hr/>
        <h2>Certifications</h2>
        <div id="certificationContainer">
            <c:forEach var="cert" items="${resume.certificationList}">
                <div class="dynamic-section">
                    <div class="form-group">
                        <label>Certification:</label>
                        <input type="text" name="certification" value="${cert.title}" />
                    </div>
                    <button type="button" class="btn-remove" onclick="removeElement(this)">Remove</button>
                </div>
            </c:forEach>
        </div>
        <button type="button" onclick="addSection('certificationContainer', 'certTemplate')">
            + Add Certification
        </button>

        <hr/>
        <h2>Academic Achievements</h2>
        <div id="achievementContainer">
            <c:forEach var="ach" items="${resume.achievementList}">
                <div class="dynamic-section">
                    <div class="form-group">
                        <label>Achievement:</label>
                        <textarea name="achievement">${ach.description}</textarea>
                    </div>
                    <button type="button" class="btn-remove" onclick="removeElement(this)">Remove</button>
                </div>
            </c:forEach>
        </div>
        <button type="button" onclick="addSection('achievementContainer', 'achTemplate')">
            + Add Achievement
        </button>

        <hr/>
        <h2>Skills</h2>
        <div class="form-group">
            <label for="skills">Skills (comma-separated):</label>
            <input type="text" id="skills" name="skills" value="${resume.skills}" />
        </div>

        <hr/>
        <input type="submit" value="Save & Generate Resume" />
    </form>


    <script type="text/template" id="eduTemplate">
        <div class="dynamic-section">
            <div class="form-group">
                <label>Qualification:</label>
                <input type="text" name="degree" />
            </div>
            <div class="form-group">
                <label>Institution:</label>
                <input type="text" name="school" />
            </div>
            <div class="form-group">
                <label>Year:</label>
                <input type="text" name="gradYear" />
            </div>
            <div class="form-group">
                <label>Score:</label>
                <input type="text" name="score" />
            </div>
        </div>
    </script>
    <script type="text/template" id="expTemplate">
        <div class="dynamic-section">
            <div class="form-group">
                <label>Job Title:</label>
                <input type="text" name="jobTitle" />
            </div>
            <div class="form-group">
                <label>Company:</label>
                <input type="text" name="company" />
            </div>
            <div class="form-group">
                <label>Years:</label>
                <input type="text" name="jobYears" />
            </div>
            <div class="form-group">
                <label>Description:</label>
                <textarea name="jobDescription"></textarea>
            </div>
        </div>
    </script>
    <script type="text/template" id="projTemplate">
        <div class="dynamic-section">
            <div class="form-group">
                <label>Project Title:</label>
                <input type="text" name="projectTitle" />
            </div>
            <div class="form-group">
                <label>Project Description:</label>
                <textarea name="projectDescription"></textarea>
            </div>
        </div>
    </script>
    <script type="text/template" id="certTemplate">
        <div class="dynamic-section">
            <div class="form-group">
                <label>Certification:</label>
                <input type="text" name="certification" />
            </div>
        </div>
    </script>
    <script type="text/template" id="achTemplate">
        <div class="dynamic-section">
            <div class="form-group">
                <label>Achievement:</label>
                <textarea name="achievement"></textarea>
            </div>
        </div>
    </script>


    <script>
        // This function removes the button's parent section
        function removeElement(buttonElement) {
            buttonElement.closest('.dynamic-section').remove();
        }

        // This function adds a new section
        function addSection(containerId, templateId) {
            const template = document.getElementById(templateId).innerHTML;
            const newSection = document.createElement('div');
            newSection.innerHTML = "<div>" + template + "</div>";

            const removeBtn = document.createElement('button');
            removeBtn.type = "button";
            removeBtn.innerText = "Remove";
            removeBtn.setAttribute('onclick', 'removeElement(this)');

            // --- This is the key ---
            // We add the "btn-remove" class so it gets the red style
            removeBtn.className = "btn-remove";

            newSection.querySelector('.dynamic-section').appendChild(removeBtn);
            document.getElementById(containerId).appendChild(newSection.querySelector('.dynamic-section'));
        }
    </script>

</body>
</html>