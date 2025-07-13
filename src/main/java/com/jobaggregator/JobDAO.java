package com.jobaggregator;

import java.sql.*;
import java.util.List;

public class JobDAO {
    public static void saveJobs(List<Job> jobs) {
        try (Connection conn = DBConnection.connect()) {
            String sql = "INSERT INTO jobs (title, company, location, link) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (Job job : jobs) {
                stmt.setString(1, job.getTitle());
                stmt.setString(2, job.getCompany());
                stmt.setString(3, job.getLocation());
                stmt.setString(4, job.getLink());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}