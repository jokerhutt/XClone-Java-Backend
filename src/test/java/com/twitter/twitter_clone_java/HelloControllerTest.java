package com.twitter.twitter_clone_java;

import io.restassured.RestAssured;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloControllerTest {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/calc")
    public Map<String, Integer> calculate(
            @RequestParam int left,
            @RequestParam int right) {
        Map<String, Integer> result = new HashMap<>();
        result.put("left", left);
        result.put("right", right);
        result.put("answer", left + right);
        return result;
    }
}