package com.twitter.twitter_clone_java;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api")
public class SampleUsers {

	private final UserRepository userRepository;
	private final UserFinder userFinder;

	public SampleUsers(UserRepository userRepository, UserFinder userFinder) {
		this.userRepository = userRepository;
		this.userFinder = userFinder;
	}

	@GetMapping("/sampleusers")
		public ResponseEntity<List<User>> getSampleUsers() {
			List<User> sampleUsers = new ArrayList<>();

			for (Long i = 1L; i < 4L; i++) {
				User sampleUser = userFinder.findUserById(i);
				System.out.println("The godfucked sampleuser is " + sampleUser.toString());
				sampleUsers.add(sampleUser);
			}
			System.out.println("Sample users is " + sampleUsers.toString());
				return ResponseEntity.ok(sampleUsers);
		}
	}
