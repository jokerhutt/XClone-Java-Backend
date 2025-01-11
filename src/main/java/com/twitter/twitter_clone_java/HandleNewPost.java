package com.twitter.twitter_clone_java;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api")

public class HandleNewPost {
	
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final PostFinder postFinder;
	
	public HandleNewPost(UserRepository userRepository, PostRepository postRepository, PostFinder postFinder) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.postFinder = postFinder;
	}
	
	@Transactional
	@PostMapping("/newpost")
	public ResponseEntity<?> newpost(@RequestBody Map<String, Object> data) {
		System.out.println("Received data " + data);
		
		Post newPost = new Post();
		newPost.setPostText((String) data.get("postTitle"));
		newPost.setCreatorId((Long) data.get("userId"));
		
		String postWords = newPost.getPostText();
		
		if (postWords.length() <= 0) {
			return ResponseEntity.badRequest().body("Cannot upload empty post");
		} else if (postWords.length() > 200){
			return ResponseEntity.badRequest().body("Post too long!");
		} else {
			postRepository.save(newPost);
		}
		
		Post preparedPost = postFinder.findByCreatorId(newPost.getCreatorId());
		return ResponseEntity.ok(preparedPost);
	}
}
