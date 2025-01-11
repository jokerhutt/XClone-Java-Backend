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
public class SamplePosts {
	
	private final PostRepository postRepository;
	private final PostFinder postFinder;
	
	public SamplePosts(PostRepository postRepository, PostFinder postFinder) {
		this.postRepository = postRepository;
		this.postFinder = postFinder;
	}
	
	@GetMapping("/sampleposts")
		public ResponseEntity<List<Post>> getSamplePosts() {
		List<Post> samplePosts = new ArrayList<>();
		
		for (Long i = 1L; i < 5L; i++) {
			Post samplePost = postFinder.findPostById(i);
			System.out.println("SAMPLE POSTS IS: " + samplePost.toString());
			samplePosts.add(samplePost);
		}
		
		return ResponseEntity.ok(samplePosts);
	}
	
	

}
