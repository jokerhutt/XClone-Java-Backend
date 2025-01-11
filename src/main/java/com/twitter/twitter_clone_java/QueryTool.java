package com.twitter.twitter_clone_java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryTool {
    public static void main(String[] args) {
        String query = "SELECT * FROM users";  // Change query as needed
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/davidglogowski/eclipse-workspace/twitter-clone/twitter_clone.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Username: " + rs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}