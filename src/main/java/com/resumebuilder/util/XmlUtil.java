package com.resumebuilder.util;

import com.resumebuilder.model.Education;
import com.resumebuilder.model.Experience;
import com.resumebuilder.model.Project;
import com.resumebuilder.model.Certification;
import com.resumebuilder.model.Achievement;
import com.resumebuilder.model.Resume;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class XmlUtil {

    /**
     * Saves (marshals) a Resume object to an XML file.
     * @param resume The Resume object to save.
     * @param filePath The full path (including filename) where the XML will be saved.
     */
    public static void saveResumeAsXml(Resume resume, String filePath) {
        try {
            // 1. Create JAXBContext
            JAXBContext context = JAXBContext.newInstance(Resume.class, Education.class, Experience.class, Certification.class, Achievement.class, Project.class);

            // 2. Create Marshaller (Java Object -> XML)
            Marshaller marshaller = context.createMarshaller();

            // 3. Set properties for nice formatting
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // 4. Create the file object
            File xmlFile = new File(filePath);

            // 5. Ensure parent directory exists
            File parentDir = xmlFile.getParentFile();
            if (!parentDir.exists()) {
                System.out.println("Creating directory: " + parentDir.getAbsolutePath());
                parentDir.mkdirs();
            }

            // 6. Marshal the object to the file
            marshaller.marshal(resume, xmlFile);

            System.out.println("SUCCESS: Resume saved to " + xmlFile.getAbsolutePath());

        } catch (JAXBException e) {
            System.out.println("ERROR: Could not save XML file.");
            e.printStackTrace();
        }
    }

    // ... (your existing saveResumeAsXml method is up here) ...

    /**
     * Loads (unmarshals) a Resume object from an XML file.
     * @param filePath The full path to the XML file.
     * @return The Resume object, or null if an error occurs.
     */
    public static Resume loadResumeFromXml(String filePath) {
        try {
            // 1. Create JAXBContext
            JAXBContext context = JAXBContext.newInstance(Resume.class, Education.class, Experience.class, Certification.class, Achievement.class, Project.class);

            // 2. Create Unmarshaller (XML -> Java Object)
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // 3. Unmarshal from the file
            File xmlFile = new File(filePath);
            if (!xmlFile.exists()) {
                System.out.println("ERROR: File not found - " + filePath);
                return null;
            }

            Resume resume = (Resume) unmarshaller.unmarshal(xmlFile);
            System.out.println("SUCCESS: Resume loaded from " + filePath);
            return resume;

        } catch (JAXBException e) {
            System.out.println("ERROR: Could not load XML file.");
            e.printStackTrace();
            return null;
        }
    }
}