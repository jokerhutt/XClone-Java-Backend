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
public class PostController {
	
	private final PostRepository postRepository;
	private final PostFinder postFinder;
	
	public PostController (PostRepository postRepository, PostFinder postFinder) {
		this.postRepository = postRepository;
		this.postFinder = postFinder;
	}
	
	@Transactional
	@PostMapping("/grabuserfollowingposts")
	public ResponseEntity<List<Post>> grabPostsFromFollowingIds(@RequestBody Map<String, List<Long>> requestBody) {
	List<Long> userFollowingIds = requestBody.get("userFollowingIds");
	List<Post> fetchedPosts = postRepository.findAllByCreatorIdIn(userFollowingIds);
	
	for (int i = 0; i < fetchedPosts.size(); i++) {
		Post currentPost = fetchedPosts.get(i);
		System.out.println("Fetched following post is: " + currentPost.toString());
	}
	
	if (fetchedPosts == null) {
		return ResponseEntity.ok(new ArrayList<>());
	} else {
		return ResponseEntity.ok(fetchedPosts);
	}
		
	}
	
	@GetMapping("getallposts")
	public ResponseEntity<List<Post>> grabPostsFromFollowingIds() {
		List<Post> allPosts = postRepository.findAll();
		return ResponseEntity.ok(allPosts);
	}
	

}
