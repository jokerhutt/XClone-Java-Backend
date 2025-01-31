package com.twitter.twitter_clone_java;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:5173")


@RestController
@RequestMapping("/api")
public class SignupController {

	private final SignupChecker signupChecker;
	private final UserRepository userRepository;

	public SignupController(SignupChecker signupChecker, UserRepository userRepository) {
		this.signupChecker = signupChecker;
		this.userRepository = userRepository;
	}
    @Transactional
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody Map<String, Object> data) {
		System.out.println("Received data " + data);

		User signupUser = new User();
		signupUser.setUsername((String) data.get("username"));
		signupUser.setPassword((String) data.get("password"));
		signupUser.setEmail((String) data.get("email"));
		signupUser.setProfilePic((String) data.get("profilePic"));
		signupUser.setDisplayName((String) data.get("displayName"));
		signupUser.setBio((String) data.get("userBio"));
		signupUser.setBackGround((String) data.get("userBackground"));

		if (signupChecker.SignupCheck(signupUser)) {
			return ResponseEntity.badRequest().body("User Already Exists");
		} else {
			userRepository.save(signupUser);
			return ResponseEntity.ok(signupUser);
		}


	}

}
