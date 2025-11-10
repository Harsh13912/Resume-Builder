package com.resumebuilder.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD) // <-- THIS WAS MISSING
public class Certification {
    private String title;

    public Certification() {}
    public Certification(String title) { this.title = title; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}