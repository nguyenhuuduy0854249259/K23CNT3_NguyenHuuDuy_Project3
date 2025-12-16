package com.example.k23cnt3_nhd_lesson05.entity;

public class Info {
    private String fullName;
    private String job;
    private String email;
    private String website;

    public Info() {
    }

    public Info(String fullName, String job, String email, String website) {
        this.fullName = fullName;
        this.job = job;
        this.email = email;
        this.website = website;
    }

    // getters & setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
}
