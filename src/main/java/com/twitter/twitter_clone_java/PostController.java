package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

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

	for (Post currentPost : fetchedPosts) {
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
	
	@Transactional
	@PostMapping("/getallpostsbypostids")
	public ResponseEntity<List<Post>> grabPostsFromPostIds(@RequestBody Map<String, List<Long>> requestBody) {
	List<Long> postIds = requestBody.get("postIds");
	List<Post> fetchedPosts = postRepository.findAllByPostIdIn(postIds);

	if (fetchedPosts == null) {
		return ResponseEntity.ok(new ArrayList<>());
	} else {
		return ResponseEntity.ok(fetchedPosts);
	}

	}


}
