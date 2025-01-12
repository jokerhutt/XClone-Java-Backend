package com.twitter.twitter_clone_java;
import org.springframework.stereotype.Component;

@Component
public class LoginChecker {
	
	private final UserRepository userRepository;
	private final UserFinder userFinder;
	
	public LoginChecker(UserRepository userRepository, UserFinder userFinder) {
		this.userRepository = userRepository;
		this.userFinder = userFinder;
	}
	
	public boolean LoginCheck (String username, String password, String email) {
		
		User loginCheckUser = this.userFinder.findUserByUsername(username);
		
		if (loginCheckUser == null) {
			return false;
		}
		
		if (loginCheckUser.getPassword().equals(password) && loginCheckUser.getEmail().equals(email)) {
			return true;
		} else {
			return false;
		}
	}

}
