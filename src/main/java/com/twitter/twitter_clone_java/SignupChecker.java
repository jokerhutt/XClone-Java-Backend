package com.twitter.twitter_clone_java;
import org.springframework.stereotype.Component;

@Component
public class SignupChecker {

	private final UserRepository userRepository;

	public SignupChecker(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean SignupCheck (User user) {
		if (this.userRepository.existsByUsername(user.getUsername())) {
			return true;
		} else if (this.userRepository.existsByEmail(user.getEmail())) {
			return true;
		} else {
			return false;
		}
	}

}
