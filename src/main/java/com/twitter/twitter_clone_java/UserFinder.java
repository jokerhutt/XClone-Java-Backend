package com.twitter.twitter_clone_java;

import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserFinder {
	
	private final UserRepository userRepository;
	
	public UserFinder(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
        		.orElseThrow(() -> new RuntimeException
        				("User not found for username: " + username));
    }
    
    public User findUserById(Long id) {
        return userRepository.findById(id)
        		.orElseThrow(() -> new RuntimeException
        				("User not found for id: " + id));
    }
    
    
    
    
}
