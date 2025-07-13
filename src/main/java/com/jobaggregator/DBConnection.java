package com.jobaggregator;

import java.sql.*;

public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:jobs.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initDB() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS jobs (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT,
                    company TEXT,
                    location TEXT,
                    link TEXT
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}