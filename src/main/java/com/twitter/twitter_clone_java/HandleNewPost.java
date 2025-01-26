package com.twitter.twitter_clone_java;

import java.util.List;
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
	private final PostMediaHandler postMediaHandler;
	
	public HandleNewPost(PostMediaHandler postMediaHandler, UserRepository userRepository, PostRepository postRepository, PostFinder postFinder) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.postFinder = postFinder;
		this.postMediaHandler = postMediaHandler;
	}
	
	@Transactional
	@PostMapping("/newpost")
	public ResponseEntity<?> newpost(@RequestBody Map<String, Object> data) {
		System.out.println("Received data " + data);
		
		Long creatorId = ((Number) data.get("userId")).longValue();
		
		List<String> postMedia = ((List<String>) data.get("postMedia"));
		
		Post newPost = new Post();
		newPost.setPostText((String) data.get("postTitle"));
		
		User creator = userRepository.findById(creatorId)
			    .orElseThrow(() -> new RuntimeException("User not found"));
		
		newPost.setCreator(creator);
		
		String postWords = newPost.getPostText();
		
		if (postWords.length() <= 0) {
			return ResponseEntity.badRequest().body("Cannot upload empty post");
		} else if (postWords.length() > 200){
			return ResponseEntity.badRequest().body("Post too long!");
		} else {
			postRepository.save(newPost);
			Long createdPostId = newPost.getPostId();
			postMediaHandler.handleAddingPostMedia(postMedia, createdPostId);
		}
		Post preparedPost = postFinder.findPostById(newPost.getPostId());
		return ResponseEntity.ok(preparedPost);
	}
}
