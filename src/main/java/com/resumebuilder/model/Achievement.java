package com.resumebuilder.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD) // <-- THIS WAS MISSING
public class Achievement {
    private String description;

    public Achievement() {}
    public Achievement(String description) { this.description = description; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}