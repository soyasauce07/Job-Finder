package com.jobaggregator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraper {

    public static List<Job> scrapeJobs(String keyword) {
        List<Job> jobList = new ArrayList<>();
        String url = "https://internshala.com/internships/keywords-" + keyword.toLowerCase().replace(" ", "-");

        try {
            Document doc = Jsoup.connect(url).get();
            Elements jobs = doc.select("div.individual_internship");

            for (Element job : jobs) {
                String title = job.select("div.heading_4_5").isEmpty() ? "No Title" : job.select("div.heading_4_5").text();
                String company = job.select("div.heading_6").text();
                String link = "https://internshala.com" + job.select("a").attr("href");

                // ✅ Visit job detail page
                String location = "Not Mentioned";
                String stipend = "Not Mentioned";
                String duration = "Not Mentioned";

                try {
                    Document detailDoc = Jsoup.connect(link).get();

                    // Extract fields from job detail page
                    location = detailDoc.select("div.other_detail_item:contains(Location)").text().replace("Location", "").trim();
                    stipend = detailDoc.select("div.other_detail_item:contains(Stipend)").text().replace("Stipend", "").trim();
                    duration = detailDoc.select("div.other_detail_item:contains(Duration)").text().replace("Duration", "").trim();

                    // Optional delay to avoid throttling
                    Thread.sleep(300);
                } catch (IOException | InterruptedException e) {
                    System.out.println("Failed to load detail page for: " + link);
                }

                Job jobObj = new Job(title, company, location, link, stipend, duration);
                jobList.add(jobObj);
            }

        } catch (IOException e) {
            System.out.println("Failed to fetch the job listing page.");
            e.printStackTrace();
        }

        return jobList;
    }
}
