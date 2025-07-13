package com.jobaggregator;

public class Job {
    private String title;
    private String company;
    private String location;
    private String link;
    private String stipend;
    private String duration;

    public Job(String title, String company, String location, String link, String stipend, String duration) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.link = link;
        this.stipend = stipend;
        this.duration = duration;
    }

    public String getTitle() {
        return title != null && !title.isEmpty() ? title : "No Title";
    }

    public String getCompany() {
        return company != null && !company.isEmpty() ? company : "No Company";
    }

    public String getLocation() {
        return location != null && !location.isEmpty() ? location : "No Location";
    }

    public String getLink() {
        return link != null && !link.isEmpty() ? link : "No Link";
    }

    public String getStipend() {
        return stipend != null && !stipend.isEmpty() ? stipend : "Not Mentioned";
    }

    public String getDuration() {
        return duration != null && !duration.isEmpty() ? duration : "Not Mentioned";
    }
}
