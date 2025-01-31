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
