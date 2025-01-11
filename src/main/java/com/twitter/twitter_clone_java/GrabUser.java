package com.twitter.twitter_clone_java;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api")
public class GrabUser {
	
	private final PostRepository postRepository;
	private final PostFinder postFinder;
	private final UserRepository userRepository;
	private final UserFinder userFinder;
	
	public GrabUser(PostRepository postRepository, PostFinder postFinder,
			UserRepository userRepository, UserFinder userFinder) {
		this.postRepository = postRepository;
		this.postFinder = postFinder;
		this.userFinder = userFinder;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/users/{postId}")
	public ResponseEntity<User> getUserByPostId(@PathVariable Long postId) {
		
//		for (int i = 0; i < 20; i++) {
//			System.out.println("Meow meow motherfucker: " + postId);
//		}
//		
		Post tempPost = postFinder.findPostById(postId);
		
		Long tempCreatorId = tempPost.getCreatorId();
		
		User tempUser = userFinder.findUserById(tempCreatorId);
		
		return ResponseEntity.ok(tempUser);
		
	}
	
	@GetMapping("/grabusers/{profileUserId}")
	public ResponseEntity<User> getUserByUserId(@PathVariable Long creatorId) {
		
		User fetchedProfileUser = userFinder.findUserById(creatorId);
		return ResponseEntity.ok(fetchedProfileUser);
		
	}
	
	@GetMapping("/grabposts/{profileUserId}")
	public ResponseEntity<List<Post>> getPostByUserId(@PathVariable Long creatorId) {
		
		for (int i = 0; i < 20; i++) {
			System.out.println("Creator id is: " + creatorId);
		}
		
		List<Post> fetchedPosts = postRepository.findAllByCreatorId(creatorId);
		return ResponseEntity.ok(fetchedPosts);
	}
	
	

}
