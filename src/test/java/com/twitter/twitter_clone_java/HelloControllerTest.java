package com.twitter.twitter_clone_java;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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