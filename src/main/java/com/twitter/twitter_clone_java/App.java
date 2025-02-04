package com.twitter.twitter_clone_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String googleCredentials = dotenv.get("GOOGLE_APPLICATION_CREDENTIALS");
        String tenorApiKey = dotenv.get("TENOR_API_KEY");
        SpringApplication.run(App.class, args);
    }
}