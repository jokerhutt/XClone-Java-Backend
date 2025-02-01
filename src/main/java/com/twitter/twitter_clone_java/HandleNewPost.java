package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api")

public class HandleNewPost {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final PostFinder postFinder;
	private final PostMediaHandler postMediaHandler;
	private final EntityManager entityManager;


	public HandleNewPost(EntityManager entityManager, PostMediaHandler postMediaHandler, UserRepository userRepository, PostRepository postRepository, PostFinder postFinder) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.postFinder = postFinder;
		this.entityManager = entityManager;
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
		}

		newPost = postRepository.save(newPost);
		Long createdPostId = newPost.getPostId();

		if (postMedia  != null && !postMedia.isEmpty()) {
			postMediaHandler.handleAddingPostMedia(postMedia, createdPostId);
		}
			postRepository.flush();
		    Post updatedPost = postRepository.findById(createdPostId)
		            .orElseThrow(() -> new RuntimeException("Post not found after media update"));
		    entityManager.refresh(updatedPost);
		    System.out.println("NEW FETCHED POST IS: " + updatedPost.toString());

			return ResponseEntity.ok(updatedPost);


	}
}
