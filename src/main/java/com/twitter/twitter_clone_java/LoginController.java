package com.twitter.twitter_clone_java;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")


@RestController
@RequestMapping("/api")
public class LoginController {
	
	private final LoginChecker loginChecker;
	private final UserRepository userRepository;
	private final UserFinder userFinder;
	
	public LoginController (UserRepository userRepository, LoginChecker loginChecker, UserFinder userFinder) {
		this.userRepository = userRepository;
		this.loginChecker = loginChecker;
		this.userFinder = userFinder;
	}
	
	@Transactional
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, Object> data) {
		System.out.println("Received data " + data);
		
		String username = (String) data.get("username");
		String password = (String) data.get("password");
		String email = (String) data.get("email");
		
		if (loginChecker.LoginCheck(username, password, email)) {
			User preparedUser = userFinder.findUserByUsername(username);
			return ResponseEntity.ok(preparedUser);
		} else {
			return ResponseEntity.badRequest().body("Incorrect username, email, or password");
		}
		
	}

}
